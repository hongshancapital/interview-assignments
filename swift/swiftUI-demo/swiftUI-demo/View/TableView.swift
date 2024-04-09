//
//  TableView.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/24.
//

import SwiftUI
import Kingfisher

struct TableView: View {
    
    @Binding var datas: [AppModel] ///< 数据源，上层传入
    @Binding var hasMore: Bool  ///< 是否还有更多数据，上传传入
    public var loadMoreBlock: (() -> Void) ///< 加载更多回调，上传传入
    public var onTapFavorite: ((_ id: String, _ isFavorite: Bool) -> Void) ///< 点击收藏回调，上传传入
    
    var body: some View {
        List {
            ForEach(self.$datas) { item in
                TableViewCell(item: item, onTapFavorite: self.onTapFavorite)
            }
            if (self.hasMore) {
                FooterLoadView(loadMoreBlock: self.loadMoreBlock).frame(alignment: .center)
            } else {
                NoMoreDataView().frame(alignment: .center)
            }
            
        }
    }
}

//struct TableView_Previews: PreviewProvider {
//    static var previews: some View {
//        TableView(datas: , hasMore: false)
//    }
//}
