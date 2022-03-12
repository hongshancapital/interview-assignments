//
//  SearchBarView.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/12/9.
//

import SwiftUI
import UIKit

struct SearchBarView: View {
    
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .padding(.leading, 5)
                .foregroundColor(.secondary)
            TextField("Search", text: $searchText, onCommit: {
                UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
            })
            Button(action: {
                searchText = ""
            }) {
                Image(systemName: "xmark.circle.fill")
                    .foregroundColor(.secondary)
                    .opacity(searchText == "" ? 0 : 1)
            }.padding(.horizontal)
        }
        .frame(height: 45, alignment: .leading)
        .cornerRadius(10)
        .background(Color(UIColor.secondarySystemBackground))
        .overlay (
            RoundedRectangle(cornerRadius: 10, style: .circular)
                .stroke(Color.gray.opacity(0.05), lineWidth: 0.5)
        )
    }
}
