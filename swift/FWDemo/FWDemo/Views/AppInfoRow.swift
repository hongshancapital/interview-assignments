//
//  AppInfoRow.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import SwiftUI

struct AppInfoRow: View {
    @State var appInfo : AppInfo
    @State var inRequesting = false
    @State var scale = 1.0
    var body: some View {
        HStack(alignment: .center){
            AsyncImage(url: URL(string: appInfo.thumbUrl)){image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            
            VStack(alignment: .leading){
                Text(appInfo.name)
                    .font(.headline)
                    .lineLimit(1)
                Spacer()
                Text(appInfo.description ?? "")
                    .font(Font.system(size: 12))
                    .foregroundColor(Color(uiColor: .darkGray))
                    .lineLimit(2)
            }
            
            Spacer()
            
            if inRequesting {
                ProgressView()
            }else {
                Image(systemName: appInfo.isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(appInfo.isFavorite ? .red : .black)
                    .onTapGesture {
                        withAnimation(Animation.easeIn(duration: 0.25)) {
                            self.scale = 0.6
                        }
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.25) {
                            withAnimation(Animation.easeOut(duration: 0.25)) {
                                self.scale = 1.0
                            }
                            appInfo.isFavorite.toggle()
                        }
                        
                        // 调用收藏\取消收藏接口
                        _ = NetworkManager.shared.sendRequest(requestType: FavoriteApis.doAppFavorite(appInfo.id))
                            .tryMap{ $0.data }
                            .decode(type: FavoriteApiResponse.self, decoder: JSONDecoder())
                            .sink(receiveCompletion: {completion in
                                print("==== favorite completion : \(completion)")
                            }, receiveValue: {response in
                                
                            })
                    }
                    .scaleEffect(scale)
            }
            
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        let appInfo = AppInfo(
            id: 123456789,
            thumbUrl: """
                https://www.baidu.com/s?tn=84053098_3_dg&wd=KakaoTalk&usm=2&ie=utf-8&rsv_pq=ac83e52700172dbf&oq=wechat&rsv_t=28d6K7gP0vkuCLIMpxWzs1Zt9flIo9fCDM6MfpN%2FX1Td%2F%2BVq5LcFJhTMbuSbu4%2FRej%2BCCA&rsv_cq=&rsv_dl=0_right_recommends_merge_21102&euri=16744c57456f4d7a95b040df3768a02a
            """,
            name: "Boloco",
            description: """
            Boloco 是我自主研发的一款创意类小App，欢迎大家下载体验，很有意思哦。
            """,
            isFavorite: false
        )
        AppInfoRow(appInfo: appInfo)
            .frame(width: 375, height: 70, alignment: .center)
    }
}
