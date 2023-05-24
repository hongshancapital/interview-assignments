//
//  RefreshScrollView.swift
//  text
//
//  Created by Harden.L on 2023/5/23.
//
import SwiftUI

let ScreenH = UIScreen.main.bounds.height
let ScreenW = UIScreen.main.bounds.width

/// 状态栏高度。非刘海屏20，X是44，11是48，12之后是47
let kStatusBarHeight = STATUSBAR_HEIGHT()
let kBottomSafeHeight = INDICATOR_HEIGHT()

/// 导航条高度
let kContentNavBarHeight = 44.0
let kNavHeight = (kStatusBarHeight + kContentNavBarHeight)
let kTabBarHeight = (49.0 + kBottomSafeHeight)

/// 状态栏高度。X是44，其他是20
func STATUSBAR_HEIGHT() ->CGFloat {
    if #available(iOS 13.0, *) {
        return getWindow()?.windowScene?.statusBarManager?.statusBarFrame.height ?? 0
    } else {
        return UIApplication.shared.statusBarFrame.height
    }
}

/// 底部指示条高度
func INDICATOR_HEIGHT() ->CGFloat {
    if #available(iOS 11.0, *) {
        return getWindow()?.safeAreaInsets.bottom ?? 0
    } else {
        return 0
    }
}

/// 获取当前设备window用于判断尺寸
func getWindow() -> UIWindow? {
    if #available(iOS 13.0, *) {
        let winScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        return winScene?.windows.first
    } else {
        return UIApplication.shared.keyWindow
    }
}

struct RefreshScrollView<Content: View>: View {
    @State private var preOffset: CGFloat = 0
    @State private var offset: CGFloat = 0
    @State private var frozen = false
    @State private var rotation: Angle = .degrees(0)
    @State private var updateTime: Date = Date()
    var offDown : CGFloat = 0.0 // 滑动内容总高
    var listH : CGFloat = 0.0 // 列表高度
    var threshold: CGFloat = 70
    @Binding var isRefreshing: Bool // 下拉刷新
    @Binding var isMore: Bool // 加载更多
    let content: Content
    
    // 下拉刷新出发回调
    var refreshTrigger: (() -> Void)?
    // 上拉加载更多
    var moreTrigger: (() -> Void)?
    
    init(_ threshold: CGFloat = 70, offDown: CGFloat, listH: CGFloat, refreshing: Binding<Bool>, isMore: Binding<Bool>, refreshTrigger: @escaping () -> Void, moreTrigger: @escaping () -> Void, @ViewBuilder content: () -> Content) {
        self.threshold = threshold
        self._isRefreshing = refreshing
        self.content = content()
        self.refreshTrigger = refreshTrigger
        self.moreTrigger = moreTrigger
        self._isMore = isMore
        self.offDown = offDown
        self.listH = listH
    }
    
    var body: some View {
//        VStack {
            List() {
                ZStack(alignment: .top) {
                    MovingPositionView()
                    VStack {
                        self.content
                            .alignmentGuide(.top, computeValue: { _ in
                                (self.isRefreshing && self.frozen) ? -self.threshold : 0
                            })
                    }
                    
                    if self.isRefreshing {
                        RefreshHeader(height: self.threshold,
                                      loading: self.isRefreshing,
                                      frozen: self.frozen,
                                      rotation: self.rotation,
                                      updateTime: self.updateTime)
                    }
                    
                }.listRowBackground(Color(red: 0.9, green: 0.9, blue: 0.9))
                    .padding(.trailing, 5)
                    .padding(.leading, 5)
                    .padding(.top, 2)
                    .padding(.bottom, 2)
                
                if isMore{
                    RefreshMore(height: self.threshold, rotation: self.rotation).onAppear(){
                        if nil != moreTrigger{
                            moreTrigger!()
                        }
                    }
                }
            }
            .background(FixedPositionView())
            .onPreferenceChange(RefreshPreferenceTypes.RefreshPreferenceKey.self) { values in
                self.calculate(values)
            }
            .onChange(of: isRefreshing) { refreshing in
                DispatchQueue.main.async {
                    if !refreshing {
                        self.updateTime = Date()
                    }
                }
            }
//        }
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
                if nil != refreshTrigger{
                    refreshTrigger!()
                }
            }
            
            if self.isRefreshing {
                if self.preOffset > self.threshold, self.offset <= self.threshold {
                    self.frozen = true
                }
            } else {
                self.frozen = false
            }
            self.preOffset = self.offset
            
            //加载更多触发条件
            print(offDown + threshold, -(self.preOffset - listH))
            if (offDown + threshold <= -(self.preOffset - listH)) && offDown > 0 {
                isMore = true
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
    
    //     位置固定不变的view
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
    
    //     位置随着滑动变化的view，高度为0
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

//MARK: - 下拉刷新UI
struct RefreshHeader: View {
    var height: CGFloat
    var loading: Bool
    var frozen: Bool
    var rotation: Angle
    var updateTime: Date
    let dateFormatter: DateFormatter = {
        let df = DateFormatter()
        df.dateFormat = "MM月dd日 HH时mm分ss秒"
        return df
    }()
    
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
                } else {
                    Image(systemName: "arrow.down")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .rotationEffect(rotation)
                }
            }
            
            .frame(width: height * 0.25, height: height * 0.8)
            .fixedSize()
            .offset(y: (loading && frozen) ? 0 : -height)
            VStack(spacing: 5) {
                Text("\(self.loading ? "正在刷新数据" : "下拉刷新数据")")
                    .foregroundColor(.secondary)
                    .font(.subheadline)
                Text("\(self.dateFormatter.string(from: updateTime))")
                    .foregroundColor(.secondary)
                    .font(.subheadline)
            }
            .offset(y: -height + (loading && frozen ? +height : 0.0))
            Spacer()
        }
        .frame(height: height)
    }
}

//MARK: - 加载更多UI
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
                Text("正在加载更多数据")
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
