package com.homo.core.rpc.base.serial;

import com.homo.core.facade.rpc.RpcContent;
import io.homo.proto.client.ParameterMsg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcHandlerInfoForServer extends RpcHandleInfo {
    public RpcHandlerInfoForServer(Class<?> rpcClazz) {
        exportInterfaceMethod(rpcClazz);
    }

    public void exportInterfaceMethod(Class<?> rpcClazz){
        if (rpcClazz.isInterface()){
            exportMethodInfos(rpcClazz);
        }
        Class<?>[] interfaces = rpcClazz.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            exportMethodInfos(anInterface);
        }
        Class<?> superclass = rpcClazz.getSuperclass();
        if (superclass != null){
            exportInterfaceMethod(superclass);
        }
    }

//    public byte[][] serializeForReturn(String funName, Object[] param) {
//        MethodDispatchInfo methodDispatchInfo = getMethodDispatchInfo(funName);
//        return methodDispatchInfo.serializeReturn(param);
//    }

    public Object[] unSerializeParamForInvokeLocalMethod(String funName, RpcContent rpcContent, Integer podId, ParameterMsg parameterMsg) {
        //内部调用
        MethodDispatchInfo methodDispatchInfo = getMethodDispatchInfo(funName);
        return methodDispatchInfo.covertToActualParam(podId,parameterMsg,rpcContent);
    }

    public Object[] unSerializeParamForInvokeLocalMethod(String funName, RpcContent rpcContent) {
        //外部调用，由proxy构建podId与prameterMsg
        MethodDispatchInfo methodDispatchInfo = getMethodDispatchInfo(funName);
        return methodDispatchInfo.covertToActualParam(rpcContent);
    }
}
