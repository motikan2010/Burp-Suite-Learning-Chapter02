package burp;

import com.motikan2010.RequestContextMenu;
import com.motikan2010.ResponseContextMenu;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory { // IContextMenuFactoryを実装する

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iBurpExtenderCallbacks.setExtensionName("Context Menu Sample");

        // コンテキストメニューを登録するために追記
        this.iBurpExtenderCallbacks.registerContextMenuFactory(this);
    }

    /**
     * コンテキストメニューの作成
     *
     * @param iContextMenuInvocation
     * @return
     */
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        /*
         * リクエストを選択した状態で、コンテキストメニューがクリックされた際に、取得される情報は「IHttpRequestResponse」クラスに格納されます
         * リクエストを複数選択した状態で、コンテキストメニュークリックすることも可能であるため、配列で取得されます。
         */
        IHttpRequestResponse[] httpRequestResponseArray = iContextMenuInvocation.getSelectedMessages();

        if (null == httpRequestResponseArray) {
            return null;
        }

        List<JMenuItem> jMenuItemList = new LinkedList<>();

        // リクエスト表示
        // コンテキストに表示するテキスト
        JMenuItem requestJMenuItem = new JMenuItem("[New Menu] Show Request : " + httpRequestResponseArray.length);
        // 右クリック時の動作を設定
        requestJMenuItem.addMouseListener(new RequestContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray));
        // コンテキストを追加
        jMenuItemList.add(requestJMenuItem);

        // レスポンス表示
        JMenuItem responseJMenuItem = new JMenuItem("[New Menu] Show Response : " + httpRequestResponseArray.length);
        responseJMenuItem.addMouseListener(new ResponseContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray));
        jMenuItemList.add(responseJMenuItem);

        return jMenuItemList;
    }
}
