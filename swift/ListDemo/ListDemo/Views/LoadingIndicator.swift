//
//  LoadingIndicator.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/22.
//

import SwiftUI

struct LoadingIndicator: View {
    
    var style: LoadingState
    
    var body: some View {
        HStack(spacing: 5.0) {
            Spacer()
            if style == .LoadMore {
                ProgressView()
                Text("Loading...")
                    .foregroundColor(.gray)
            } else {
                Text("No more data.")
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .padding()
        .background(Color.clear)
    }
}

struct LoadingIndicator_Previews: PreviewProvider {
    static var previews: some View {
        LoadingIndicator(style: .Loading)
        LoadingIndicator(style: .LoadComplete)
    }
}
