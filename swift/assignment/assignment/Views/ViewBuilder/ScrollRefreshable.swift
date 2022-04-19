//
//  ScrollRefreshable.swift
//  assignment
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import SwiftUI

struct ScrollRefreshable<Content: View>: View {
    private var content: Content
    var onRefresh: () async -> ()
    init(title: String,tintColor: Color,@ViewBuilder content: @escaping () -> Content, onRefresh: @escaping () async->()){
        self.content = content()
        self.onRefresh = onRefresh
        UIRefreshControl.appearance().attributedTitle = NSAttributedString(string: title)
        UIRefreshControl.appearance().tintColor = UIColor(tintColor)
    }
    var body: some View {
        List{
            content
                .listRowSeparatorTint(.clear)
                .listRowBackground(Color.clear)
                .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        }
        .listStyle(.insetGrouped)
        .refreshable {
            await onRefresh()
        }
    }
}

struct ScrollRefreshable_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
