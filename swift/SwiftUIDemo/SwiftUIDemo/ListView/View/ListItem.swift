//
//  ListViewItem.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/18.
//

import SwiftUI

/// 单个列表Item
/// 左边显示图标，中间显示Title和Subtitle，右侧显示收藏按钮
struct ListItem: View {

    let model : ListItemModel
    
    var onTapLike : ((_ model : ListItemModel) -> Void)?
    
    @State private var clickScale = 1.0
    
    var body: some View {
        ZStack{
            HStack() {
                /// 左侧Icon
                ListItemIconImage(url: model.icon)
                    .padding(.trailing,10)
                /// 中间的Title和Subtitle
                VStack (alignment:.leading,spacing:4) {
                    Text(model.title).foregroundColor(.black)
                    Text(model.description).foregroundColor(.gray)
                }
                .padding(.trailing,10)
                .frame(maxWidth:.infinity)
                /// 右侧的喜欢按钮
                Image(model.isLike == true ? "ic_like" : "ic_unlike")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24, alignment: .center)
                    .scaleEffect(clickScale)
                    .onTapGesture {
                        /// 点击Item的收藏按钮
                        withAnimation {
                            if (model.isLike) {
                                clickScale = 1.0
                            } else {
                                clickScale = 1.2
                            }
                        }
                        onTapLike?(model)
                    }
            }.padding(.all,16)
        }
        .frame(height: 80)
        .background(.white)
        .cornerRadius(8)
    }
}

struct ListItemIconImage : View {
    
    let url : String
    
    var body: some View {
        AsyncImage(url: URL(string: url)) { image in
            image.resizable().scaledToFit()
        } placeholder: {
            LoadingProgressView()
        }
        .cornerRadius(8)
        .frame(width: 52, height: 52)
    }
}


//MARK: - 预览
struct ListViewItem_Previews: PreviewProvider {
    static var previews: some View {

        let jsonMap : [String:Any] = [
            "artworkUrl100":"https://is5-ssl.mzstatic.com/image/thumb/Purple112/v4/1c/f5/e7/1cf5e7c0-7327-41e0-bb87-44eceafa2b64/ProductionAppIcon-1x_U007emarketing-0-7-0-0-0-85-220.png/100x100bb.jpg",
            "description" : "Join the conversation! Retweet, chime in on a thread, go viral, or just scroll through the Twitter timeline to stay on top of what everyone’s talking about. ",
            "trackCensoredName" : "Twitter",
            "trackId" : 12345678,
        ]
        
        let data = (try? JSONSerialization.data(withJSONObject:jsonMap, options: .prettyPrinted))!
        
        let itemModel = try? JSONDecoder().decode(ListItemModel.self, from: data)
        
        ListItem(model: itemModel!)
            .previewLayout(.sizeThatFits)
            .padding(.all,16)
    }
}
