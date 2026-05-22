//

//
//  AppModelRow.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/1.
//  
//
    

import SwiftUI

struct AppDataRow: View {
    
    @ObservedObject var viewModel: AppDataRowViewModel
    
    // 动画效果
    @State private var isLikeButtonScale: CGFloat = 1.0
    
    var body: some View {
        HStack(alignment: .center, spacing: 10) {
            // 图标
            AsyncImage(url: URL(string: viewModel.appData.icon)) { image in
                image.resizable()
            } placeholder: {
                // 加载中占位placeholder
                ProgressView()
            }
            .frame(width: 60, height: 60)
            .cornerRadius(10)
            .overlay(RoundedRectangle(cornerRadius: 10, style: .continuous).stroke(Color(red: 220/255.0, green: 220/255.0, blue: 220/255.0), lineWidth: 1))
            // 文字内容
            VStack(alignment: .leading, spacing: 5) {
                Text(viewModel.appData.name)
                    .font(Font(UIFont.systemFont(ofSize: 15, weight: .medium)))
                    .lineLimit(1)
                Text(viewModel.appData.desc)
                    .font(Font(UIFont.systemFont(ofSize: 12, weight: .regular)))
                    .lineLimit(2)
            }
            Spacer()
            // 喜欢按钮
            Button {
                viewModel.changeIsLike {
                    // 如果更改为喜欢，播放动画效果
                    if viewModel.appData.isLike {
                        withAnimation {
                            self.isLikeButtonScale = 1.5
                        }
                        
                        withAnimation(Animation.linear.delay(0.2)) {
                            self.isLikeButtonScale = 1.0
                        }
                    }
                }
            } label: {
                Image(viewModel.appData.isLike ? "like" : "dislike")
                    .resizable()
                    .scaleEffect(self.isLikeButtonScale)
            }
            .frame(width: 25, height: 25)
        }
        .padding(10)
        .background(Color.white)
        .cornerRadius(10)
    }
}

struct AppDataRow_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
