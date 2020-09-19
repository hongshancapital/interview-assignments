import SwiftUI

struct OffsetAnimation: AnimatableModifier {
    var offset: CGFloat = 0

    var animatableData: CGFloat {
        get { offset }
        set { offset = newValue }
    }

    func body(content: Content) -> some View {
        content.offset(y: offset)
    }
}

extension View {
    func offsetAnimation(_ offset: CGFloat) -> some View {
        modifier(OffsetAnimation(offset: offset))
    }
}
