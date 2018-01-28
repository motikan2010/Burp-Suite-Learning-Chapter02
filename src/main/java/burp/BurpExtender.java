package burp;

import com.motikan2010.RequestContextMenu;
import com.motikan2010.ResponseContextMenu;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iBurpExtenderCallbacks.setExtensionName("Context Menu Sample");
        this.iBurpExtenderCallbacks.registerContextMenuFactory(this);
    }

    /**
     * コンテキストメニューの作成
     *
     * @param iContextMenuInvocation
     * @return
     */
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        IHttpRequestResponse[] httpRequestResponseArray = iContextMenuInvocation.getSelectedMessages();
        if (null == httpRequestResponseArray) {
            return null;
        }

        List<JMenuItem> jMenuItemList = new LinkedList<>();

        // リクエスト表示
        JMenuItem requestJMenuItem = new JMenuItem("[New Menu] Show Request : " + httpRequestResponseArray.length);
        requestJMenuItem.addMouseListener(new RequestContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray));
        jMenuItemList.add(requestJMenuItem);

        // レスポンス表示
        JMenuItem responseJMenuItem = new JMenuItem("[New Menu] Show Response : " + httpRequestResponseArray.length);
        responseJMenuItem.addMouseListener(new ResponseContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray));
        jMenuItemList.add(responseJMenuItem);

        return jMenuItemList;
    }
}
