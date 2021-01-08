//
//  SearchView.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import SwiftUI

struct SearchView : View {
    @EnvironmentObject var store: SearchStore
    
    private var searchText: String { return store.state.searchText }
    private var isSearching: Bool { return store.state.isSearching }
    
    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .padding(.leading, 16)
            TextField("Tap here to search", text: $store.state.searchText, onEditingChanged: { value in
                if value { store.state.didSearch = false }
                withAnimation { store.state.isSearching = true }
            }, onCommit: {
                store.send(.search(text: searchText))
            })
            Button(action: {
                UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
                withAnimation {
                    store.send(.cancel)
                }
            }) {
                Image(systemName: "xmark")
                    .foregroundColor(.darkerGray)
                    .opacity(isSearching ? 1 : 0)
            }
            .padding(12)
        }
        .background(Color.lightGray)
        .cornerRadius(10.0)
        .padding(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
        .navigationBarHidden(isSearching)
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}
