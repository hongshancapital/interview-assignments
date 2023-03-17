//
//  UserCellView.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/10.
//

import SwiftUI

struct UserCellView: View {
    
    @State private var showSelected: Bool = false
    var item: User
    
    init(data: User) {
        item = data
    }
    
    var body: some View {
        Group {
            HStack(alignment:.center) {
                if #available(iOS 15.0, *) {
                    AsyncImage(url: URL(string: item.artworkUrl60))
                        .scaledToFit()
                        .frame(width: 70, height: 70)
                        .border(Color(hex: 0xdddddd))
                        .cornerRadius(5)
                }
                
                VStack(alignment: .leading, spacing: 5) {
                    Text(item.trackName)
                        .bold()
                        .font(.system(size: 20))
                        .lineLimit(1)
                    
                    Text(item.description)
                        .font(.system(size: 16))
                        .lineLimit(2)
                }
                
                Spacer()
                
                Button {
                    showSelected.toggle()
                } label: {
                    VStack(alignment: .center) {
                        Spacer()
                        if showSelected {
                            Image(systemName: "heart")
                                .foregroundColor(.red)
                                .frame(width: 40, height: 40, alignment: .trailing)
                        } else {
                            Image(systemName: "heart.fill")
                                .foregroundColor(.gray)
                                .frame(width: 40, height: 40, alignment: .trailing)
                        }
                        Spacer()
                    }
                }
            }
            .padding(defaultListItemPadding)
            .frame(width: UIScreen.main.bounds.width-20, height: 100, alignment: .leading)
            .background(RoundedRectangle(cornerRadius: 12).foregroundColor(.white))
        }.padding(5)
    }
}

//struct UserCellView_Previews: PreviewProvider {
//    static var previews: some View {
//        UserCellView(data: User())
//    }
//}
