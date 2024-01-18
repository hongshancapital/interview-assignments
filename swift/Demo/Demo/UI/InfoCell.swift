//
//  InfoCell.swift
//  Demo
//
//  Created by milomiao on 2022/6/21.
//

import SwiftUI

struct RowConst {
    static let imageWH : CGFloat = 50
    static let likeImageWH : CGFloat = 30
    static let rowHeight : CGFloat = 60
}

struct InfoCell: View {
    @ObservedObject var info: AppItemInfo
    @EnvironmentObject var provider: AppDataProvider
    
    var body: some View {
        HStack {
            if let icon = info.iconURL, let url = URL(string: icon) {
                AsyncImage(url: url) { image in
                    image.resizable()
                } placeholder: {
                    ProgressView()
                }
                .frame(width: RowConst.imageWH, height: RowConst.imageWH, alignment: .center)
                .cornerRadius(5)
                .padding(10)
            }
            
            if let title = info.title, let subtitle = info.subtitle {
                VStack(alignment: .leading) {
                    Text(title)
                        .font(.subheadline)
                        .bold()
                        .padding(0)
                        .frame(maxWidth:.infinity, alignment: .leading)
                    Text(subtitle)
                        .font(.footnote)
                        .padding(0)
                        .frame(maxWidth:.infinity, alignment: .leading)
                }
                .frame(maxWidth:.infinity)
                
                Image(systemName: info.likeIcon)
                    .onTapGesture {
                        self.toggleLike()
                    }
                    .frame(width: (RowConst.likeImageWH),
                           height: (RowConst.likeImageWH),
                           alignment: .center)
                    .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 10))
                    .foregroundColor(info.isLike ? .red : .gray)
                    .scaleEffect(info.isLike ? 1.2 : 1.0)
                    .animation(.default, value: info.isLike)
            }
        }
        .frame(height: RowConst.rowHeight)
    }
    
    func toggleLike() {
        info.toggleLike()
        
        if let _ = info.cancellable {
            return
        }
        
        info.cancellable = info.$isLike.sink(receiveValue: { like in
            if (like) {
                provider.store.update(info.id!)
            } else {
                provider.store.remove(info.id!)
            }
        })
    }
}

struct InfoCell_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            InfoCell(info: createInfo())
        }
        .frame(height: RowConst.rowHeight)
    }
    
    static func createInfo() -> AppItemInfo {
        let appInfo = AppItemInfo()
        appInfo.title = "hello world"
        appInfo.subtitle = "hello world hello world hello world hello world !"
        appInfo.iconURL = "https://www.hko.gov.hk/images/HKOWxIconOutline/pic53.png"
        appInfo.isLike = false
        return appInfo
    }
}
