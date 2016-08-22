package com.atongmu.sample.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * [Description]
 * date: 2016/8/11
 *
 * @author maofagui
 * @version 1.0
 */
public class FutureTest {
    //模拟个场景：
    //2个定时任务，每个执行2s，
    //1个大小为2线程池，定时调度3s。
    //捕获定时任务的超时事件，并重启。
    public static void main(String[] args) {
        int timeout=3;
        SubTask<String> subTask1 =new SubTask<String>();
        SubTask<String> subTask2 =new SubTask<String>();
        SubTask<String> subTask3 =new SubTask<String>();
        SubTask<String> subTask4 =new SubTask<String>();
        List<SubTask<String>> list = new ArrayList<SubTask<String>>();
        list.add(subTask1);
        list.add(subTask2);
        list.add(subTask3);
        list.add(subTask4);

        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(2);

        List<Future<String>> futureList = new ArrayList<Future<String>>();

        while(1==1){
            futureList.clear();
            for(SubTask<String> task:list) {
                Future<String> future = executorService.schedule(task, 0, TimeUnit.SECONDS);
                futureList.add(future);
            }

            for(Future<String> future:futureList) {
                String futureRes ="";
                try {
                    futureRes = future.get(timeout, TimeUnit.SECONDS);
                    System.out.println("get是阻塞的！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    System.out.println("我去 超时了。");
                }finally {
                    System.out.println(future.cancel(true));
                    System.out.println(futureRes);
                }
            }
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }







//        Future<String> future1 = executorService.schedule(subTask1, 0, TimeUnit.SECONDS);
//        Future<String> future2 = executorService.schedule(subTask2, 0, TimeUnit.SECONDS);
//        String res="";
//        String res2="";
//        try {
//            res = future1.get(timeout, TimeUnit.SECONDS);
//            res2 = future2.get(timeout, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            future1.cancel(true);
//            //future1 = executorService.schedule(subTask1, 1, TimeUnit.SECONDS);
//            e.printStackTrace();
//        }finally {
//            System.out.println(res);
//            System.out.println(res2);
//        }
//        Future<String> future1 = executorService.submit(subTask1);
//        Future<String> future2 = executorService.submit(subTask2);
    }
}



class SubTask<String> implements Callable{

    public String call() throws Exception {
        String name = (String) Thread.currentThread().getName();
        int sleepTime = new Random().nextInt(10);
        System.out.println("开始执行："+name+" "+sleepTime+"秒。");
        Thread.sleep(sleepTime * 1000);
        System.out.println("执行完了:" + name);
        return (String) "ok";
    }
}