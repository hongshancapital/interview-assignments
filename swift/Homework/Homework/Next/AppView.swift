//
//  AppView.swift
//  Homework
//
//  Created by miao jiafeng on 2022/6/12.
//

import SwiftUI

struct AppView: View {
    
    @State var appData: AppData
    
    var body: some View {
        HStack{
            AsyncImage(url: URL(string: appData.appUrl)) { Image in
                // 调整大小以适应实际的大小
                Image.resizable()
            } placeholder: {
                // 占位图
                ProgressView()
                    .progressViewStyle(.circular)
            }
            .frame(width: 60, height: 60)
            .cornerRadius(5)
            .overlay (
                RoundedRectangle(cornerRadius: 5,style:  .continuous)
                    .stroke(.gray, lineWidth: 0.5)
            )
            
            VStack(alignment: HorizontalAlignment.leading, spacing: 4, content: {
                Text(appData.appName)
                    .font(.title2)
                    .lineLimit(1)
                    
                Text(appData.appDescription)
                    .font(.caption)
                    .lineLimit(2)
            })
            
            Spacer(minLength: 5)
            Image(appData.isLike ? "like":"no_like")
                .resizable()
                .frame(width: 28, height: 28)
                .onTapGesture {
                    appData.isLike = appData.isLike ? false : true
                }
        }
    }
}

struct AppView_Previews: PreviewProvider {
    static var previews: some View {
        AppView(appData: listData[0])
    }
}
