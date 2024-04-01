//
//  ListItemViewModel.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/19.
//

import Foundation

/// 单个item的监听
class ListItemViewModel : ObservableObject {
    
    /// 是否点亮了右侧的喜欢按钮
    @Published var isLike = false
    
    /// 当前是否正在加载左侧的icon图标
    @Published var isLoadingIcon = true
    
    let model : ListItemModel
    
    init(model : ListItemModel) {
        self.model = model
    }
    
    /// 点击喜欢按钮，点亮或取消
    func actionForClickLikeButton() {
        isLike = !isLike
    }
    
    func onIconImageDownloadSuccess() {
        isLoadingIcon = false
    }
    
}
