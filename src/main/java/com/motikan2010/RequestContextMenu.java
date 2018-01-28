package com.motikan2010;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

public class RequestContextMenu implements MouseListener {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IHttpRequestResponse[] iHttpRequestResponseArray;

    private RequestResponse requestResponse;

    public RequestContextMenu(IBurpExtenderCallbacks callbacks, IHttpRequestResponse[] requestResponseArray) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iHttpRequestResponseArray = requestResponseArray;
        this.requestResponse = new RequestResponse(callbacks);
    }

    /**
     * マウスボタンを離すと呼び出される
     *
     * @param e イベント
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        PrintWriter stdout = new PrintWriter(iBurpExtenderCallbacks.getStdout(), true);
        for (IHttpRequestResponse iHttpRequestResponse : this.iHttpRequestResponseArray) {
            String requestString = requestResponse.showRequest(iHttpRequestResponse);
            stdout.println(requestString);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
