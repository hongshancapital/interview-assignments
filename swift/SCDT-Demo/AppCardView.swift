//
//  AppCardView.swift
//  SCDT-Demo
//
//  Created by wuzhe on 10/17/22.
//

import SwiftUI

struct AppCardView: View {
    @State var favorited: Bool
    
    var appInformation: AppInfomation
    
    init(appInfo: AppInfomation) {
        appInformation = appInfo
        self.favorited = FavoriteAppManager.shared.hasFavorited(appInfo.id)
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
            
            thumbnailImage
            
            VStack(alignment: .leading, spacing: 4){
                Text(appInformation.title)
                    .cardTitleStyle()
                Text(appInformation.subtitle)
                    .cardSubtitleStyle()
            }
            .padding(EdgeInsets(top: 0, leading: 9, bottom: 0, trailing: 0))
            
            favoriteButton
        }
    }
    
    private var thumbnailImage: some View {
        Group{
            AsyncImage(url: URL(string: appInformation.thumbnail)){ phase in
                if let image = phase.image {
                    image.resizable().scaledToFit()
                } else if phase.error != nil {
                    Color.gray
                } else {
                    ProgressView().scaleEffect(37.0/34.0)
                }
            }
            
        }
        .frame(width: 54, height: 54, alignment: .center)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(RoundedRectangle(cornerRadius: 12).stroke(.gray, lineWidth: 0.5))
    }
    
    private var favoriteButton: some View {
        Button (action: {
            withAnimation(.easeIn(duration: 0.2)){
                favorited.toggle()
                FavoriteAppManager.shared.favorite(appInformation.id
                                                   , favorited: favorited)
            }
        }, label: {
            Image(systemName: favorited ? "heart.fill" : "heart")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 24, height: 18, alignment: .center)
                .foregroundColor(favorited ? Color.red : Color.gray)
                .onAppear{
                }
        })
        .frame(width: 50, height: 60, alignment: .center)
        .scaleEffect(favorited ? 1.2 : 1.0)
        .buttonStyle(.plain)
    }
}

extension Text {
    func cardTitleStyle() -> some View {
        lineLimit(1)
        .font(.headline)
        .frame(maxWidth:.infinity, alignment: .leading)
    }
    
    func cardSubtitleStyle() -> some View {
        font(.footnote)
        .lineSpacing(-10)
        .lineLimit(2)
        .allowsTightening(true)
        .truncationMode(.tail)
    }
}

extension AppCardView{
    func appListCellStyle() -> some View{
        frame(height:98)
            .listRowBackground(
                RoundedRectangle(cornerRadius: 14)
                    .foregroundColor(.white)
                    .padding(EdgeInsets(top: 6, leading: 0, bottom: 6, trailing: 0))
            )
            .listRowSeparator(.hidden)
            .listRowInsets(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 0))
    }
}

struct AppCardView_Previews: PreviewProvider {
    static var previews: some View {
        AppCardView(appInfo: AppInfomation(id:123423,title:"BUmble",subtitle:"Millisons of people have signed up for Bumble to start building valuable relationships", thumbnail: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/c2/1d/14/c21d1495-6354-ca11-82ce-6695ea9d88fa/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg"))
            .previewDevice("iPhone 13 Pro Max")
        AppCardView(appInfo: AppInfomation(id:123,title:"13",subtitle:"24", thumbnail: "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/15/1e/f9/151ef91a-4017-8e4f-034c-048596341f23/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg"))
            .previewDevice("iPhone 13 Pro Max")
    }
}
