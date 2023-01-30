//
//  SimpleRefreshingView.swift
//  Demo
//
//  Created by 方林威 on 2023/1/30.
//

import SwiftUI

struct SimpleRefreshingView: View {
    var body: some View {
        HStack(spacing: 10) {
            ProgressView()
            Text("Loading...")
                .foregroundColor(.secondary)
        }
    }
}

struct SimpleRefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        SimpleRefreshingView()
    }
}
