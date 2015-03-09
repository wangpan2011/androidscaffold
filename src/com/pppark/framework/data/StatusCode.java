package com.pppark.framework.data;

public class StatusCode {
    public static final int STATUS_OK = 0;// 状态码0表示成功
    public static final int STATUS_EXCEPTION = 1024;// 状态码表示异常
    public static final int STATUS_JSON_EXCEPTION = STATUS_EXCEPTION + 1;// 状态码表示json解析异常
    public static final int ERROR_NETWORK = STATUS_JSON_EXCEPTION + 1;// 无网络

    public static final int ERROR_MALFORMATURL = ERROR_NETWORK + 1;//

    public static final int ERROR_CONNECTION = ERROR_MALFORMATURL + 1;//
    public static final int ERROR_SETPOST = ERROR_CONNECTION + 1;
    public static final int ERROR_WRITEDATA = ERROR_SETPOST + 1;
    public static final int ERROR_GETRESPONSECODE = ERROR_WRITEDATA + 1;
    public static final int ERROR_ENCRYPTOR = ERROR_GETRESPONSECODE + 1;// 加密出错
    public static final int ERROR_DECRYPTOR = ERROR_ENCRYPTOR + 1;// 解密出错
    public static final int ERROR_GETINPUTSTREAM = ERROR_DECRYPTOR + 1;// 获取输入流出错

    public static final int ERROR_SNIFFER_FAIL = ERROR_GETINPUTSTREAM + 1;// 嗅探出错

    public static final int ERROR_UNKNOWNHOST_FAIL = ERROR_SNIFFER_FAIL + 1;// 查询dns出错，用于报告无网络连接的情况

    public static final int ERROR_IO_UNKNOWNHOST = ERROR_UNKNOWNHOST_FAIL + 1;

    public static final int HC_EXECUTE_MalformedURLException = ERROR_IO_UNKNOWNHOST + 1;
    public static final int HC_EXECUTE_BindException = HC_EXECUTE_MalformedURLException + 1;
    public static final int HC_EXECUTE_UnknownHostException = HC_EXECUTE_BindException + 1;
    public static final int HC_EXECUTE_NoRouteToHostException = HC_EXECUTE_UnknownHostException + 1;
    public static final int HC_EXECUTE_PortUnreachableException = HC_EXECUTE_NoRouteToHostException + 1;
    public static final int HC_EXECUTE_ConnectException = HC_EXECUTE_PortUnreachableException + 1;
    public static final int HC_EXECUTE_HttpRetryException = HC_EXECUTE_ConnectException + 1;
    public static final int HC_EXECUTE_ProtocolException = HC_EXECUTE_HttpRetryException + 1;
    public static final int HC_EXECUTE_UnknownServiceException = HC_EXECUTE_ProtocolException + 1;
    public static final int HC_EXECUTE_SocketTimeoutException = HC_EXECUTE_UnknownServiceException + 1;
    public static final int HC_EXECUTE_SocketException = HC_EXECUTE_SocketTimeoutException + 1;
    public static final int HC_EXECUTE_IOException = HC_EXECUTE_SocketException + 1;
    public static final int HC_EXECUTE_Exception = HC_EXECUTE_IOException + 1;

    public static final int HC_toString_ConnectException = HC_EXECUTE_Exception + 1;
    public static final int HC_toString_HttpRetryException = HC_toString_ConnectException + 1;
    public static final int HC_toString_ProtocolException = HC_toString_HttpRetryException + 1;
    public static final int HC_toString_SocketTimeoutException = HC_toString_ProtocolException + 1;
    public static final int HC_toString_SocketException = HC_toString_SocketTimeoutException + 1;
    public static final int HC_toString_IOException = HC_toString_SocketException + 1;
    public static final int HC_toString_ParseException = HC_toString_IOException + 1;
    public static final int HC_toString_UnsupportedCharsetException = HC_toString_ParseException + 1;
    public static final int HC_toString_IllegalArgumentException = HC_toString_UnsupportedCharsetException + 1;

    public static final int STATUS_NO_MODIFED = HC_toString_IllegalArgumentException + 1;

}
