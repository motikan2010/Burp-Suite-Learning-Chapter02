package com.motikan2010;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import com.motikan2010.util.RequestResponseUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

public class RequestContextMenu implements MouseListener {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IHttpRequestResponse[] iHttpRequestResponseArray;
    private RequestResponseUtils requestResponseUtils;

    public RequestContextMenu(IBurpExtenderCallbacks callbacks, IHttpRequestResponse[] requestResponseArray) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iHttpRequestResponseArray = requestResponseArray;
        this.requestResponseUtils = new RequestResponseUtils(callbacks);
    }

    /**
     * マウスボタンを離すと呼び出される
     *
     * @param event イベント
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        PrintWriter stdout = new PrintWriter(iBurpExtenderCallbacks.getStdout(), true);
        for (IHttpRequestResponse iHttpRequestResponse : this.iHttpRequestResponseArray) {
            // リクエストを取得
            String requestString = requestResponseUtils.showRequest(iHttpRequestResponse);

            // リクエストを出力
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
