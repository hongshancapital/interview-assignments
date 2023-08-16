//
//  HomePageRow.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import SwiftUI

struct HomePageRow: View {
    
    // 是否展示加载loading true展示 false不展示
    @State var showProgressView = false
    // 是否收藏 true收藏 false为收藏
    @State var isCollected = true
    
    let model: HomePageViewModel
    
    var body: some View {
        HStack {
            Spacer()
            if showProgressView {
                ZStack {
                    ProgressView()
                }
                .frame(
                    width: 60,
                    height: 60,
                    alignment: .center
                )
            } else {
                Image(model.iconName)
                    .frame(
                        width: 60,
                        height: 60,
                        alignment: .center
                    )
                    .cornerRadius(10)
            }
            Spacer()
            VStack(alignment: .leading) {
                Spacer()
                Text(model.rowTitle)
                    .font(.system(size: 15))
                    .fontWeight(.bold)
                    .lineLimit(1)
                Spacer()
                Text(model.rowContent)
                    .font(.system(size: 12))
                    .fontWeight(.regular)
                    .lineLimit(2)
                Spacer()
            }
            Spacer()
            Button {
                self.isCollected.toggle()
            } label: {
                Image(systemName: isCollected ? "heart.fill": "heart")
                    .foregroundColor(isCollected ? .red : .gray)
            }
            Spacer()
        }
        .frame(height: 80)
        .background(Color.white)
        .cornerRadius(10)
    }
}

struct HomePageRow_Previews: PreviewProvider {
    static var previews: some View {
        let homePageItem = HomePageItem(id: 1, artworkUrl100: "11", trackCensoredName: "111", description: "2222")
        let model = HomePageViewModel(homePageItem: homePageItem)
        HomePageRow(model: model)
    }
}
