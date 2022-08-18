//
//  AppListRowView.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import SwiftUI

/// 应用列表单个应用信息视图
struct AppListRowView: View {
    @Environment(\.colorScheme) var colorScheme
    
    /// 应用数据
    @Binding var appInfo: AppInfoModel
    
    /// 按钮点击事件回调，为nil时按钮状态在内部处理，不为nil时由外部处理状态变更
    var action: ButtonAction

    var body: some View {
        VStack {
            HStack(alignment: .center) {
 
                AppIconView(url: $appInfo.logoUrl)
                    .frame(width: 60, height: 60, alignment: .center)
                
                Spacer().frame(width: 10)
                
                VStack(alignment: .leading) {
                    Text(appInfo.name)
                        .frame(maxWidth:.infinity, alignment: .leading)
                        .font(.headline)
                        .fixedSize(horizontal: false, vertical: true)
                        .lineLimit(2)
                        .lineSpacing(2)
                    
                    Spacer().frame(height:5)
                    
                    Text(appInfo.description)
                        .fontWeight(.light)
                        .font(.system(size: 12))
                        .fixedSize(horizontal: false, vertical: true)
                        .lineLimit(2)
                        .lineSpacing(2)
                }
                
                HeartButton(isSelected: appInfo.liked, action: action)
                .frame(width: 40, height: 80)
            }
            .padding(EdgeInsets(top: 15, leading: 15, bottom: 15, trailing: 5))
            .frame(height: 90)
            .background(colorScheme == .dark ? Color.black : Color.white)
            .cornerRadius(10)
        }
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
    }
}

struct AppListRowView_Previews: PreviewProvider {
    
    @State static var appInfo = AppInfoModel(appId: 111, name: "APP名称", logoUrl: "https://c-ssl.duitang.com/uploads/item/202007/15/20200715230722_aGyuP.jpeg", description: "APP应用程序的相关简介描述", liked: false)
    
    static var previews: some View {
        AppListRowView(appInfo: $appInfo, action: {
        })
            .frame(width: .infinity, height: 80)
    }
}
