package com.enjoygolf24.api.common.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ServiceTimeChecker {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(ServiceTimeChecker.class);

    /**
     *　サービスの実装時間をログに出力する。
     * @param joinPoint JoinPoint
     * @return オブジェクト
     * @throws Throwable エラー
     */
    @Around(value = "execution(* *..*ServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        //        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        //        Object[] args = joinPoint.getArgs();
        //        StringBuilder data = new StringBuilder();
        //        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //        for (int argIndex = 0; argIndex < args.length; argIndex++) {
        //            for (Annotation paramAnnotation : parameterAnnotations[argIndex]) {
        //
        //                Field[] fields = paramAnnotation.getClass().getFields();
        //                for (Field field : fields) {
        //                    Annotation[] ann = field.getAnnotations();
        //                    for (Annotation annotation : ann) {
        //
        //                    }
        //
        //                }
        //
        //
        //                data.append(args[argIndex]);
        //            }
        //        }
        long startTime = System.currentTimeMillis();
        Object rtn = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;

        logger.info("serviceImpl Time Taken by {} is {}", joinPoint, timeTaken);
        joinPoint.getArgs();

        return rtn;
    }
}
