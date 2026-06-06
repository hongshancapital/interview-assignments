import SwiftUI

struct RefreshScrollView<Content: View>: View {
    @State private var preOffset: CGFloat = 0
    @State private var offset: CGFloat = 0
    @State private var frozen = false
    @State private var rotation: Angle = .degrees(0)
    
    var offDown : CGFloat = 0.0 // 滑动内容总高
    var listH : CGFloat = 0.0 // 列表高度
    var threshold: CGFloat
    @Binding var isRefreshing: Bool // 下拉刷新
    @Binding var isMoreLoading: Bool // 加载更多
    let content: Content
    
    var canLoadMore: Bool = true
    
    // 下拉刷新出发回调
    var refreshTrigger: (() -> Void)?
    // 上拉加载更多
    var moreTrigger: (() -> Void)?
    
    init(_ threshold: CGFloat = 60,
         offDown: CGFloat,
         listH: CGFloat,
         refreshing: Binding<Bool>,
         isMoreLoading: Binding<Bool>,
         canLoadMore: Bool,
         refreshTrigger: @escaping () -> Void,
         moreTrigger: @escaping () -> Void,
         @ViewBuilder content: () -> Content) {
        self.threshold = threshold
        self._isRefreshing = refreshing
        self.content = content()
        self.refreshTrigger = refreshTrigger
        self.moreTrigger = moreTrigger
        self._isMoreLoading = isMoreLoading
        self.offDown = offDown
        self.listH = listH
        self.canLoadMore = canLoadMore
    }
    
    var body: some View {
        VStack {
            ScrollView(showsIndicators: false, content: {
                ZStack(alignment: .top) {
                    MovingPositionView()
                    VStack {
                        self.content
                            .alignmentGuide(.top, computeValue: { _ in
                                (self.isRefreshing && self.frozen) ? -self.threshold : 0
                            })
                    }
                    RefreshHeader(height: self.threshold,
                                  loading: self.isRefreshing,
                                  frozen: self.frozen,
                                  rotation: self.rotation)
                }
                
                if isMoreLoading{
                    RefreshMore(height: self.threshold, rotation: self.rotation).onAppear(){
                        if nil != moreTrigger{
                            moreTrigger!()
                        }
                    }
                }
            })
            .background(FixedPositionView())
            .onPreferenceChange(RefreshPreferenceTypes.RefreshPreferenceKey.self) { values in
                self.calculate(values)
            }
            .onChange(of: isRefreshing) { refreshing in
            }
        }
    }
    
    func calculate(_ values: [RefreshPreferenceTypes.RefreshPreferenceData]) {
        DispatchQueue.main.async {
            /// 计算croll offset
            let movingBounds = values.first(where: { $0.viewType == .movingPositionView })?.bounds ?? .zero
            let fixedBounds = values.first(where: { $0.viewType == .fixedPositionView })?.bounds ?? .zero
            self.offset = movingBounds.minY - fixedBounds.minY
            self.rotation = self.headerRotation(self.offset)
            
            /// 触发刷新
            if !self.isRefreshing, self.offset > self.threshold, self.preOffset <= self.threshold {
                self.isRefreshing = true
            }
            
            if self.isRefreshing {
                if self.preOffset > self.threshold, self.offset <= self.threshold {
                    self.frozen = true
                    if nil != refreshTrigger {
                        refreshTrigger!()
                    }
                }
            } else {
                self.frozen = false
            }
            self.preOffset = self.offset
            
            //加载更多触发条件
            if (offDown + threshold <= -(self.preOffset - listH)) && offDown > 0 && self.canLoadMore == true {
                isMoreLoading = true
            }
        }
    }
    
    func headerRotation(_ scrollOffset: CGFloat) -> Angle {
        if scrollOffset < self.threshold * 0.60 {
            return .degrees(0)
        } else {
            let h = Double(self.threshold)
            let d = Double(scrollOffset)
            let v = max(min(d - (h * 0.6), h * 0.4), 0)
            return .degrees(180 * v / (h * 0.4))
        }
    }
    
    //位置固定不变的view
    struct FixedPositionView: View {
        var body: some View {
            GeometryReader { proxy in
                Color
                    .clear
                    .preference(key: RefreshPreferenceTypes.RefreshPreferenceKey.self,
                                value: [RefreshPreferenceTypes.RefreshPreferenceData(viewType: .fixedPositionView, bounds: proxy.frame(in: .global))])
                
            }
        }
    }
    
    //位置随着滑动变化的view，高度为0
    struct MovingPositionView: View {
        var body: some View {
            GeometryReader { proxy in
                Color
                    .clear
                    .preference(key: RefreshPreferenceTypes.RefreshPreferenceKey.self,
                                value: [RefreshPreferenceTypes.RefreshPreferenceData(viewType: .movingPositionView, bounds: proxy.frame(in: .global))])
            }
            .frame(height: 0)
        }
    }
}

//下拉刷新UI
struct RefreshHeader: View {
    var height: CGFloat
    var loading: Bool
    var frozen: Bool
    var rotation: Angle
    
    var body: some View {
        HStack(spacing: 20) {
            Spacer()
            Group {
                if self.loading {
                    VStack {
                        Spacer()
                        ActivityRep()
                        Spacer()
                    }
                }
            }
            .frame(width: height * 0.25, height: height * 0.8)
            .fixedSize()
            .offset(y: -height)
            Spacer()
        }
        .frame(height: height)
    }
}

//加载更多UI
struct RefreshMore: View{
    var height: CGFloat
    var rotation: Angle
    
    var body: some View{
        HStack(spacing: 20) {
            Spacer()
            Group {
                VStack {
                    Spacer()
                    ActivityRep()
                    Spacer()
                }
            }
            .frame(width: height * 0.25, height: height * 0.8)
            .fixedSize()
            VStack() {
                Text("加载更多")
                    .foregroundColor(.secondary)
                    .font(.subheadline)
                
            }
            Spacer()
        }
        .frame(height: height)
    }
}

struct RefreshPreferenceTypes {
    enum ViewType: Int {
        case fixedPositionView
        case movingPositionView
    }
    
    struct RefreshPreferenceData: Equatable {
        let viewType: ViewType
        let bounds: CGRect
    }
    
    struct RefreshPreferenceKey: PreferenceKey {
        static var defaultValue: [RefreshPreferenceData] = []
        static func reduce(value: inout [RefreshPreferenceData],
                           nextValue: () -> [RefreshPreferenceData]) {
            value.append(contentsOf: nextValue())
        }
    }
}

struct ActivityRep: UIViewRepresentable {
    func makeUIView(context: UIViewRepresentableContext<ActivityRep>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView()
    }
    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<ActivityRep>) {
        uiView.startAnimating()
    }
}
