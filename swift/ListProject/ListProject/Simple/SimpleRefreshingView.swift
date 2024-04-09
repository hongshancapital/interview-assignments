//
//  SimpleRefreshingView.swift
//  ListProject
//
//  Created by shencong on 2022/6/8.
//

import SwiftUI

struct SimpleRefreshingView: View {
    var body: some View {
        ActivityIndicator(style: .medium)
    }
}

struct SimpleRefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        SimpleRefreshingView()
    }
}
