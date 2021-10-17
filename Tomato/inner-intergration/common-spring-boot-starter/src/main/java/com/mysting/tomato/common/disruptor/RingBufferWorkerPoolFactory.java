package com.mysting.tomato.common.disruptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import com.mysting.tomato.common.disruptor.consumer.Consumer;
import com.mysting.tomato.common.disruptor.message.DataWrapper;
import com.mysting.tomato.common.disruptor.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;

public class RingBufferWorkerPoolFactory {

	private static class SingletonHolder {
		static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
	}

	private RingBufferWorkerPoolFactory() {

	}

	public static RingBufferWorkerPoolFactory getInstance() {
		return SingletonHolder.instance;
	}

	private static Map<String, Producer> producers = new ConcurrentHashMap<>();

	private static Map<String, Consumer> consumers = new ConcurrentHashMap<>();

	private RingBuffer<DataWrapper> ringBuffer;

	private SequenceBarrier sequenceBarrier;

	private WorkerPool<DataWrapper> workerPool;

	public void initAndStart(ProducerType producerType, int bufferSize, WaitStrategy waitStrategy,
			Consumer... consumers) {
		// 构建ringBuffer
		this.ringBuffer = RingBuffer.create(producerType, new EventFactory<DataWrapper>() {
			@Override
			public DataWrapper newInstance() {
				return new DataWrapper();
			}
		}, bufferSize, waitStrategy);

		// 配置序号栅栏
		this.sequenceBarrier = this.ringBuffer.newBarrier();
		// 配置工作池
		this.workerPool = new WorkerPool<>(this.ringBuffer, this.sequenceBarrier, new EventExceptionHander(),
				consumers);
		// 把所有的消费者放入池中
		for (Consumer consumer : consumers) {
			this.consumers.put(consumer.getConsumerId(), consumer);
		}
		
		// 添加sequences
		this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
		//自定义线程池
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(10);
		// 设置最大线程数
		executor.setMaxPoolSize(200);
		// 设置队列容量
		executor.setQueueCapacity(100);
		// 允许线程的空闲时间(秒)：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
		executor.setKeepAliveSeconds(600);
		// 设置默认线程名称
		executor.setThreadNamePrefix("disruptor-");
		// 核心线程在规定时间内会被回收，默认为false
		executor.setAllowCoreThreadTimeOut(true);
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		// 启动工作池
		this.workerPool.start(executor);

	}

	public Producer getProducer(String producerId) {

		Producer producer = this.producers.get(producerId);
		if (producer == null) {
			producer = new Producer(this.ringBuffer);
			this.producers.put(producerId, producer);

		}
		return producer;

	}

	static class EventExceptionHander implements ExceptionHandler<DataWrapper> {

		private static Logger logger = LoggerFactory.getLogger(EventExceptionHander.class);
		@Override
		public void handleEventException(Throwable ex, long sequence, DataWrapper event) {
			logger.error( "Disruptor Exception processing: " + sequence + ex.getMessage()  );
		}

		@Override
		public void handleOnShutdownException(Throwable ex) {
			logger.error( "Disruptor Exception on shutdown: " + ex.getMessage()  );
		}

		@Override
		public void handleOnStartException(Throwable ex) {
			logger.error( "Disruptor Exception on start: " + ex.getMessage()  );
		}

	}

}
