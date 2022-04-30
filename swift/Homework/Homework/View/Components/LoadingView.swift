//
// Homework
// LoadingView.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import SwiftUI

struct LoadingView: View {
    var noMoreData: Bool
    
    var body: some View {
        content
            .font(.subheadline)
            .foregroundColor(.gray)
    }
    
    @ViewBuilder
    var content: some View {
        if noMoreData {
            HStack {
                Spacer()
                Text("No More Data.")
                Spacer()
            }
        } else {
            HStack {
                Spacer()
                ActivityIndicatorView()
                Text("Loading...")
                Spacer()
            }
        }
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView(noMoreData: false)
        LoadingView(noMoreData: true)
    }
}
