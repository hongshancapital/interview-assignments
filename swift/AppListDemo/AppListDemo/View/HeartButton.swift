//
//  HeartButton.swift
//  AppListDemo
//
//  Created by HQ on 2022/8/19.
//

import SwiftUI

typealias ButtonAction = () -> Void

/// 可点击切换状态的心形按钮
struct HeartButton: View {
    
    /// 当前按钮是否为选中状态
    @State var isSelected: Bool
    
    /// 按钮点击事件回调
    var action: ButtonAction
    
    var body: some View {
        Button {
            // isSelected不使用 @Binding修饰，在action()回调中再处理数据源中对应状态的变更，
            // 否则整个列表都会以动画形式刷新，导致图片有闪屏现象
            withAnimation(.spring(response: 0.4, dampingFraction: 0.2, blendDuration: 0)) {
                isSelected.toggle()
                action()
            }
        } label: {
            Label("", systemImage: isSelected ? "heart.fill" : "heart")
                .labelStyle(.iconOnly)
                .foregroundColor(isSelected ? .red : .gray)
                .scaleEffect(isSelected ? 1.3 : 1, anchor: .center)
            
        }
        // 设置buttonStyle，只要不是.automatic时都可以避免列表点击时也触发按钮点击事件的问题
        .buttonStyle(.plain)
    }
}

struct HeartButton_Previews: PreviewProvider {
    @State static var isSelected = true
    static var previews: some View {
        HeartButton(isSelected: isSelected) {
        }
    }
}
