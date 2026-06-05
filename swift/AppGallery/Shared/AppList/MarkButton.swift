//
//  MarkButton.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import SwiftUI

typealias MarkProcessor = () -> Void

struct MarkButton: View {
    
    private var marked: Bool
    
    private var markProcessor: MarkProcessor?
    
    init(marked: Bool, markProcessor: MarkProcessor?) {
        self.marked = marked
        self.markProcessor = markProcessor
    }
    
    var body: some View {
        Button {
            withAnimation(.spring()) {
                self.markProcessor?()
            }
        } label: {
            Label(marked ? "marked": "not mark yet",
                  systemImage: marked ? "heart.fill": "heart")
            .labelStyle(.iconOnly)
            .foregroundColor(.gray)
            .symbolRenderingMode(marked ? .multicolor: .monochrome)
            .scaleEffect(marked ? 1.2: 1, anchor: .center)
        }
    }
}

struct MarkButton_Previews: PreviewProvider {
    static var previews: some View {
        MarkButton(marked: false, markProcessor: nil)
    }
}
