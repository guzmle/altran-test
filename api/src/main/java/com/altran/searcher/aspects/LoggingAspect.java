package com.altran.searcher.aspects;

import org.apache.logging.log4j.Level;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que controla la ejecucion del regitro de los logs de auditoria y debuggeo
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    private static final String AUDIT = "AUDIT";
    private static final String INIT_DEBUG = "Entrando a metodo {} Parametros: {}";
    private static final String END_DEBUG = "Saliendo del metodo {} Parametros: {}";
    private static final String AUDIT_MESSAGE = "Invoked {} method {}";

    /**
     * Metodo que permite el registro de entrada de todos los metodos del sistema
     * con el objetivo de depuracion del sistema si es requerido
     *
     * @param joinPoint objeto que posee la informacion del metodo interceptado
     */
    @Before("execution(* com.altran.searcher.*.*.*(..))")
    public void before(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug(INIT_DEBUG, joinPoint.toShortString(), joinPoint.getArgs());
        }
    }

    /**
     * Metodo que permite el registro de la salida de todos los metodos con el objetivo
     * de depuracion del sistema si es requerido
     *
     * @param joinPoint objeto que posee la informacion del metodo interceptado
     */
    @After("execution(* com.altran.searcher.*.*.*(..))")
    public void after(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug(END_DEBUG, joinPoint.toShortString());
        }
    }

    /**
     * Metodo que permite el registro de la entrada del endpoint del api con el objetivo de registrar
     * la informacion de la invocacion con fines de auditoria
     *
     * @param joinPoint objeto que posee la informacion del metodo interceptado
     */
    @Before("execution(* com.altran.searcher.controllers.*.* (..))")
    public void beforeControllerExecute(JoinPoint joinPoint) {
        if (logger.isEnabled(Level.getLevel(AUDIT))) {
            logger.log(Level.getLevel(AUDIT), AUDIT_MESSAGE, joinPoint.toShortString(), joinPoint.getArgs());
        }
    }
}
