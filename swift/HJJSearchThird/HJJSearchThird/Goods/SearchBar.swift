//
//  SearchBar.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import SwiftUI

struct SearchBar: View {
    
    @Binding var hideImage: Bool
    @Binding var text: String
    
    var body: some View {
        ZStack {
            TextField("Tap here to search", text: $text)
                .modifier(ClearButton(text: $text))
                .padding(7)
                .padding(.horizontal, hideImage ? 25 : 5)
                .foregroundColor(.primary)
                .background(Color("searchBar_background"))
                .cornerRadius(8)
                .padding(.horizontal, 10)
                
            Image(systemName: "magnifyingglass")
                .foregroundColor(.gray)
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 16)
                .opacity(hideImage ? 1.0 : 0.0)
        }

    }

}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(hideImage: .constant(false), text: .constant(""))
    }
}

public struct ClearButton: ViewModifier {
    @Binding var text: String

    public init(text: Binding<String>) {
        self._text = text
    }

    public func body(content: Content) -> some View {
        HStack {
            content
            Spacer()
            Image(systemName: "multiply")
                .foregroundColor(.secondary)
                .opacity(text == "" ? 0 : 1)
                .onTapGesture { self.text = "" }
        }
    }
}
