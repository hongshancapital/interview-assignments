import SwiftUI

public struct PagingScrollView<Content: View>: View {
    private let content: () -> Content
    private let refreshingAction: () -> Void
    @Binding var isRefreshing: Bool
    @State private var scrollViewSize: CGSize = .zero
    @State private var contentSize: CGSize = .zero
    @State private var contentOffset: CGPoint = .zero
    @State private var refreshFooterSize: CGSize = .zero
    @State private var topSafeAreaInset: CGFloat = 0

    private let maxPullOffset: CGFloat = 100
    private let spaceName = "FrameLayer"

    public init(isRefreshing: Binding<Bool>, refreshingAction: @escaping () -> Void, @ViewBuilder content: @escaping () -> Content) {
        _isRefreshing = isRefreshing
        self.refreshingAction = refreshingAction
        self.content = content
    }

    public var body: some View {
        GeometryReader { geometry in
            ZStack {
                ScrollView {
                    VStack {
                        self.contentOffsetReader
                        self.content()
                            .offset(y: self.isRefreshing ? -self.maxPullOffset : 0)
                    }
                    .background(self.contentSizeReader)
                }
                .viewSize(geometry.size)
                self.refreshFooter
            }
            .safeAreaInsets(geometry.safeAreaInsets)
        }
        .coordinateSpace(name: spaceName)
        .clipped()
        .onPreferenceChange(ContentOffsetKey.self) { offset in
            self.contentOffset = offset ?? .zero
        }
        .onPreferenceChange(ContentSizeKey.self) { size in
            self.contentSize = size ?? .zero
        }
        .onPreferenceChange(ViewSizeKey.self) { size in
            self.scrollViewSize = size ?? .zero
        }
        .onPreferenceChange(SafeAreaInsetsKey.self) { insets in
            self.topSafeAreaInset = insets?.top ?? 0
        }
//        .disabled(isRefreshing)
    }

    @ViewBuilder
    private var refreshFooter: some View {
        if contentSize.height > scrollViewSize.height {
            RefreshFooter(isRefreshing: $isRefreshing)
                .frame(height: maxPullOffset)
                .offsetAnimation(refreshFooterOffset)
                .background(refreshViewSizeReader)
                .onPreferenceChange(ViewSizeKey.self) { value in
                    self.refreshFooterSize = value ?? .zero
                }
        }
    }

    // MARK: Reader

    private var contentOffsetReader: some View {
        GeometryReader { geometry in
            Color.clear
                .contentOffset(geometry.frame(in: .named(self.spaceName)).origin)
        }
        .frame(height: 0)
    }

    private var contentSizeReader: some View {
        GeometryReader { geometry in
            Color.clear
                .contentSize(geometry.frame(in: .local).size)
        }
    }

    private var refreshViewSizeReader: some View {
        GeometryReader { geometry in
            Color.clear
                .viewSize(geometry.size)
        }
    }

    // MARK: Accessors

    private var bottomContentOffset: CGFloat {
        guard !isRefreshing else { return -maxPullOffset }
        let offset = contentSize.height - scrollViewSize.height + contentOffset.y - topSafeAreaInset
        if offset >= 0 {
            return 0
        } else {
            if offset < -maxPullOffset {
                DispatchQueue.main.async {
                    // Toggle refresh action
                    self.isRefreshing = true
                    self.refreshingAction()
                }
            }
            return offset
        }
    }

    private var refreshFooterOffset: CGFloat {
        return (scrollViewSize.height + refreshFooterSize.height) / 2 + bottomContentOffset
    }
}

struct PagingScrollView_Previews: PreviewProvider {
    static var previews: some View {
        PagingScrollView(isRefreshing: .constant(true), refreshingAction: {}) {
            Text("Hello world")
        }
    }
}

extension CGPoint {
    static func - (left: CGPoint, right: CGPoint) -> CGPoint {
        return CGPoint(x: left.x - right.x, y: left.y - right.y)
    }
}
