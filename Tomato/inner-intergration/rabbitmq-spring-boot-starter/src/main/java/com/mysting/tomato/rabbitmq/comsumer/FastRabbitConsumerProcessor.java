package com.mysting.tomato.rabbitmq.comsumer;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.mysting.tomato.rabbitmq.annotation.FastRabbitListener;
import com.mysting.tomato.rabbitmq.common.DetailResponse;
import com.mysting.tomato.rabbitmq.config.RabbitMQAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 队列监听处理器，监听所有@FastRabbitListener方法
 */
@AutoConfigureAfter(RabbitMQAutoConfigure.class)
@Slf4j
public class FastRabbitConsumerProcessor implements BeanPostProcessor {
    @Autowired
    FastBuildRabbitMqConsumer fastRabbitConsumer;
    private Set<Class<?>> nonAnnotatedClasses = new HashSet<>();
    @Autowired
    private Environment environment;

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        if (!this.nonAnnotatedClasses.contains(targetClass)) {
            //扫描bean内带有Scheduled注解的方法
            Map<Method, Set<FastRabbitListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                    (MethodIntrospector.MetadataLookup<Set<FastRabbitListener>>) method -> {
                        Set<FastRabbitListener> annotatedMethod =
                                AnnotatedElementUtils.getMergedRepeatableAnnotations(method,
                                        FastRabbitListener.class, null);
                        return (!annotatedMethod.isEmpty() ? annotatedMethod : null);
                    });
            if (annotatedMethods.isEmpty()) {
                //如果这个class没有注解的方法，缓存下来，因为一个class可能有多个bean
                this.nonAnnotatedClasses.add(targetClass);
                if (log.isTraceEnabled()) {
                    log.trace("No @FastRabbitListener annotations found on bean class: " + bean.getClass());
                }
            } else {
                // Non-empty set of methods
                Set<Map.Entry<Method, Set<FastRabbitListener>>> entries = annotatedMethods.entrySet();
                //根据@FastRabbitListener注解方法的数量创建线程池
                for (Map.Entry<Method, Set<FastRabbitListener>> entry : entries) {
                    Method method = entry.getKey();
                    for (FastRabbitListener fastRabbitListener : entry.getValue()) {
                        //处理这些有Scheduled的方法
                        process(fastRabbitListener, method, bean);
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug(annotatedMethods.size() + " @FastRabbitListener methods processed on bean '" + beanName +
                            "': " + annotatedMethods);
                }
            }
        }
        return bean;
    }

    private void process(FastRabbitListener fastRabbitListener, Method method, Object bean) {
        for (int i = 0; i < Convert.toInt(fastRabbitListener.concurrency()); i++) {
            ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(10, 20);
            threadPoolExecutor.execute(new RabbitmqListenerHander(fastRabbitListener, method, bean));
        }
    }

    private class RabbitmqListenerHander implements Runnable {
        private FastRabbitListener fastRabbitListener;
        private Method method;
        private Object bean;

        RabbitmqListenerHander(FastRabbitListener fastRabbitListener, Method method, Object bean) {
            this.fastRabbitListener = fastRabbitListener;
            this.method = method;
            this.bean = bean;
        }

        @Override
        public void run() {
            while (true) {
                MessageConsumer direct = null;
                try {
                    direct = fastRabbitConsumer.buildMessageConsumer(
                            environment.resolvePlaceholders(fastRabbitListener.exchange()),
                            environment.resolvePlaceholders(fastRabbitListener.routingKey()),
                            environment.resolvePlaceholders(fastRabbitListener.queue()),
                            new MessageProcess<Object>() {
                                @Override
                                public DetailResponse process(Object message) {
                                    Method invocableMethod = AopUtils.selectInvocableMethod(method, bean.getClass());
                                    try {
                                        if (message instanceof byte[]) {
                                            invocableMethod.invoke(bean,
                                                    new Message(new String((byte[]) message).getBytes(), null));
                                        } else {
                                            invocableMethod.invoke(bean,
                                                    new Message(JSONUtil.toJsonStr(message).getBytes(), null));
                                        }
                                        return new DetailResponse() {{
                                            setIfSuccess(true);
                                        }};
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return new DetailResponse() {{
                                            setIfSuccess(false);
                                            setErrMsg(e.getMessage());
                                        }};
                                    }
                                }
                            }, environment.resolvePlaceholders(fastRabbitListener.type()));
                    log.info("Rabbitmq:监听通道{}连接成功！", environment.resolvePlaceholders(fastRabbitListener.queue()));
                    while (true) {
                        try {

                            direct.consume();
                        } catch (Exception e) {
                            log.error("Rabbitmq:连接{}断开,尝试重连....",
                                    environment.resolvePlaceholders(fastRabbitListener.queue()));
                            break;
                        }
                    }

                } catch (IOException e) {
                    log.error("Rabbitmq:重连失败！10秒后重连！");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
