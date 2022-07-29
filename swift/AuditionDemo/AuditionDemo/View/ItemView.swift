//
//  ItemView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -单条信息View
struct ItemView: View {
    //MARK: - 属性
    var dict:Item
    //MARK: - Body
    var body: some View {
        HStack(spacing:0){
            WebImageView(urls:Util.sharedInstance.getImageUrls(dict))                       //图标区
                .padding(0)
            
            VStack(alignment: .leading,spacing: 0){                                         //文字区
                
                Text( dict.trackName.replacingOccurrences(of: "\n", with: "") )             //为了头条暂时效果需要将换行符替换
                    .bold()
                    .lineLimit(1)
                    .font(.system(size: 18))
                    .frame(alignment: .leading)
                
                Text( dict.description.replacingOccurrences(of: "\n", with: "") )           //为了头条暂时效果需要将换行符替换
                    .lineLimit(2)
                    .font(.system(size: 14))
                    .frame(alignment: .leading)
                
            }//: VStack
            .padding(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 10))
            .frame(alignment: .center)
            
            Spacer()
            
            FavouriteView(trackId: dict.trackId)                                            //收藏图标区
                .padding(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 10))
        }//: HStack
        .background(Color.white)
        .cornerRadius(12)
        .accentColor(Color.gray)
    }
}

//MARK: - Preview
struct ItemView_Previews: PreviewProvider {
    static var previews: some View {
        ItemView(dict: Item.init(trackId: 100, trackName: "Google Chat", description: "description", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg", artworkUrl100: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/100x100bb.jpg", artworkUrl512: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg"))
    }
}
