//
//  SearchView.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import SwiftUI

struct SearchBar: View {
    @Binding var text: String
    @Binding var isEditing: Bool
    var commitHandler: (() -> Void)?
    var cancelHandler: (() -> Void)?

    var body: some View {
        HStack {
            if !isEditing {
                Image(systemName: "magnifyingglass").foregroundColor(.gray)
            }
            TextField("Tap here for search", text: $text, onEditingChanged: { value in
                withAnimation { self.isEditing = value }
            }, onCommit: {
                self.commitHandler?()
            })
            if isEditing {
                Button(action: { self.clearContent() }, label: {
                    Image(systemName: "xmark").foregroundColor(.gray)
                })
            }
        }
        .padding(.horizontal)
        .padding(.vertical, Spacing.threeQuarter)
        .background(Color.gray.opacity(0.1))
        .cornerRadius(Spacing.threeQuarter)
    }

    // MARK: Intents
    private func clearContent() {
        cancelHandler?()
        withAnimation { self.isEditing = false }
        // Resign first responder
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(text: .constant(""), isEditing: .constant(false))
    }
}
