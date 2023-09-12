//
//  FLUserRow.swift
//  demo
//
//  Created by 张帅 on 2023/4/8.
//

import SwiftUI
import Combine

struct FLUserRow: View {
    enum Constant {
        static let cellHeight: CGFloat = 73
        static let cellCornerRadius: CGFloat = 8
        static let margin: CGFloat = 16
        static let pading: CGFloat = 5
    }
    @EnvironmentObject var viewModel: FLUserViewModel
    @State var user: FLUser
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: Constant.cellCornerRadius).foregroundColor(.white).frame(height: Constant.cellHeight).padding(EdgeInsets(top: Constant.pading, leading: Constant.margin, bottom: Constant.pading, trailing: Constant.margin))
            HStack (spacing: 0){
                self.viewModel.userImages[user.artistId].map { image in
                    Image(uiImage: image)
                        .resizable()
                        .scaledToFit()
                        .cornerRadius(Constant.cellCornerRadius)
                        .background(Color.clear)
                        .padding()
                   
                }
                VStack(alignment: .leading) {
                    Text(user.trackName).frame(alignment: .leading).fontWeight(.bold)
                    Text(user.description)
                }.padding(Constant.pading)
                Spacer()
                var isLike: Bool = (user.islike == nil) ? false : user.islike!
                let imageName = isLike ? "like" : "dislike"
                Image(imageName)
                    .onTapGesture {
                        isLike = !isLike
                        user.islike = isLike
                    }.padding(.trailing)
            }
            .padding(.leading)
            .frame(height: Constant.cellHeight)
            .onAppear {
                viewModel.getImage(for: user)
            }
        }
    }
}

struct FLUserRow_Previews: PreviewProvider {
    static var previews: some View {
        let user1 = FLUser(artistId: 3333, description: "点击卡迪那", trackName: "低年级看时候", artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/dd/18/24/dd1824a2-4d2e-8340-8e20-4fc96282d83a/AppIcon-1x_U007emarketing-0-7-0-85-220.png/100x100bb.jpg")
        
        FLUserRow(user: user1)
    }
}
