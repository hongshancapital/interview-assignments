//
//  LoadingMoreView.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import SwiftUI

struct LoadingMoreView: View {
    enum Constants {
        static let loadingText = "Loading"
        static let noMoreDataText = "No more data."
        static let horizationSpacing: CGFloat = 10
    }
    
    let noMoreData: Bool
    @State var id: Int = 0
    
    init(noMoreData: Bool) {
        self.noMoreData = noMoreData
    }
    
    var body: some View {
        HStack(spacing: Constants.horizationSpacing) {
            Spacer()
            
            if !noMoreData {
                ProgressView()
                    .id(id)
                    .onAppear {
                        id += 1
                    }
                
                VStack {
                    Text(Constants.loadingText)
                        .foregroundColor(.secondary)
                        .font(.subheadline)
                }
            } else {
                Text(Constants.noMoreDataText)
                    .foregroundColor(.secondary)
                    .font(.subheadline)
            }
            
            Spacer()
        }
        .frame(minWidth: .zero, maxWidth: .infinity)
    }
}

struct LoadingMoreView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingMoreView(noMoreData: false)
    }
}

struct LoadingMoreViewWithNoMoreData_Previews: PreviewProvider {
    static var previews: some View {
        LoadingMoreView(noMoreData: true)
    }
}

