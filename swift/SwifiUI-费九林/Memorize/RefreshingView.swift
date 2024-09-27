//
//  RefreshingView.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/28.
//

import SwiftUI

struct RefreshingView: View {
    var body: some View {
        ActivityIndicator(style: .medium)
    }
}

struct RefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        RefreshingView()
    }
}
