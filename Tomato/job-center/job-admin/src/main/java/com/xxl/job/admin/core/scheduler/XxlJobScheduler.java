package com.xxl.job.admin.core.scheduler;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.thread.*;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xuxueli 2018-10-28 00:18:17
 */

public class XxlJobScheduler  {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobScheduler.class);


    public void init() throws Exception {
        // init i18n
    	// 初始化国际化文件
        initI18n();

        // admin registry monitor run
        // 启动注册监控器（将注册到register表中的IP加载到group表）/ 30执行一次
        // 实时更新调度中心中的执行器状态
        // 1、每30秒，从xxl_job_registry表中获取注册的执行器记录，根据update_time判断是否存活
        // 2、如果update_time超过90秒没有更新，就视作离线，从xxl_job_registry中移除
        // 3、然后将xxl_job_registry的registry_key和xxl_job_group的app_name相同的匹配上，生成dao对象，这个对象中有address_list的值，最后会更新到xxl_job_group中的address_list中
        // 4、如果已经从xxl_job_registry移除了，那么xxl_job_group就匹配不到，dao对象的address_list就是null，所以会把xxl_job_group中的address_list改为null
        JobRegistryMonitorHelper.getInstance().start();

        // admin fail-monitor run
        // 负责处理调度失败的任务，AlarmStatus标记该失败任务有没有处理，0表示未处理
        // 1、查询AlarmStatus等于0且调度失败的任务（包括没发送成功调度任务和任务执行失败）
        // 2、把AlarmStatus从0改成-1，表示正在处理，根据失败任务找到它的任务
        // 3、重新调度任务（重新执行次数等于executor_fail_retry_count值）
        // 4、如果需要邮件提醒，那么就发邮件。如果发送成功，就把alarmStatus改成2，发送失败改成3，不需要发邮件就改成1
        JobFailMonitorHelper.getInstance().start();

        // admin lose-monitor run
        // 负责处理发出了请求，但调度器没有响应的任务
        // 1、查询调度成功，但超过10分钟没有响应的任务，并且目标执行器地址不在xxl_job_registry表中，即目标执行器离线了
        // 2、将该任务的handleCode设置成500
        JobLosedMonitorHelper.getInstance().start();

        // admin trigger pool start
        // 创建2个线程池，为下面的发送调度任务做准备
        JobTriggerPoolHelper.toStart();

        // admin log report start
        // 统计每天运行成功的任务、运行失败的任务。把统计结果存入xxl_job_log_report表
        JobLogReportHelper.getInstance().start();

        // start-schedule
        // 启动定时任务调度器（执行任务，缓存任务）
        JobScheduleHelper.getInstance().start();

        logger.info(">>>>>>>>> init xxl-job admin success.");
    }

    
    public void destroy() throws Exception {

        // stop-schedule
        JobScheduleHelper.getInstance().toStop();

        // admin log report stop
        JobLogReportHelper.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

        // admin lose-monitor stop
        JobLosedMonitorHelper.getInstance().toStop();

        // admin fail-monitor stop
        JobFailMonitorHelper.getInstance().toStop();

        // admin registry stop
        JobRegistryMonitorHelper.getInstance().toStop();

    }

    // ---------------------- I18n ----------------------

    private void initI18n(){
        for (ExecutorBlockStrategyEnum item:ExecutorBlockStrategyEnum.values()) {
            item.setTitle(I18nUtil.getString("jobconf_block_".concat(item.name())));
        }
    }

    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<String, ExecutorBiz>();
    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address==null || address.trim().length()==0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorBiz executorBiz = executorBizRepository.get(address);
        if (executorBiz != null) {
            return executorBiz;
        }

        // set-cache
        executorBiz = new ExecutorBizClient(address, XxlJobAdminConfig.getAdminConfig().getAccessToken());

        executorBizRepository.put(address, executorBiz);
        return executorBiz;
    }

}
