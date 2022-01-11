//
//  SearchBar.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI
/// SearchBar class
///
/// only show TextField
///
///
struct SearchBar: View {
    var placeholder: String = "Search"

    @Binding var text: String
    let searchTextChangedAction: ((String) -> Void)?
    var body: some View {
        HStack {
            TextField(self.placeholder,
                      text: $text).onChange(of: text) { _ in
                self.searchTextChangedAction?(text)
            }
            if text != "" {
                Image(systemName: "xmark.circle.fill")
                    .imageScale(.medium)
                    .foregroundColor(Color(.systemGray3))
                    .padding(3)
                    .onTapGesture {
                        withAnimation {
                            self.text = ""
                        }
                    }
            }
        }.padding(10)
            .background(Color(.systemGray6))
            .cornerRadius(12)
            .padding(.vertical, 10)
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(text: .constant(""), searchTextChangedAction: {
            _ in

        }).previewLayout(.fixed(width: 375, height: 60))
    }
}
