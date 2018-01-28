package com.motikan2010;

import burp.*;

import java.io.UnsupportedEncodingException;

public class RequestResponse {

    private static final String SEPARATOR = " ";
    private static final String NEW_LINE = System.lineSeparator();

    IBurpExtenderCallbacks iBurpExtenderCallbacks;
    IExtensionHelpers iExtensionHelpers;

    public RequestResponse(IBurpExtenderCallbacks callbacks) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iExtensionHelpers = callbacks.getHelpers();
    }

    /**
     * リクエスト情報を取得
     *
     * @param iHttpRequestResponse
     * @return String
     */
    public String showRequest(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        IRequestInfo iRequestInfo = this.iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        for (String header : iRequestInfo.getHeaders()) {
            stringBuilder.append(header + NEW_LINE);
        }

        // リクエストボディ情報を取得
        String requestBody = getBody(iHttpRequestResponse.getRequest());
        if (requestBody.length() > 0) {
            stringBuilder.append(NEW_LINE + requestBody);
        }

        return stringBuilder.toString();
    }

    /**
     * レスポンス情報を取得
     *
     * @param iHttpRequestResponse
     * @return String
     */
    public String showResponse(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        IResponseInfo iResponseInfo = this.iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse());

        // レスポンスヘッダ情報を取得
        for (String header : iResponseInfo.getHeaders()) {
            stringBuilder.append(header + NEW_LINE);
        }

        // レスポンスボディ情報を取得
        String requestBody = getBody(iHttpRequestResponse.getResponse());
        if (requestBody.length() > 0) {
            stringBuilder.append(NEW_LINE + requestBody);
        }

        return stringBuilder.toString();
    }

    /**
     *
     * @param reqResBytes
     * @return String
     */
    private String getBody(byte[] reqResBytes) {
        String response = null;
        try {
            response = new String(reqResBytes, "UTF-8");
            response = response.substring(this.iExtensionHelpers.analyzeResponse(reqResBytes).getBodyOffset());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error converting string");
        }
        return response;
    }
}
