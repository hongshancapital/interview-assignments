//
//  FrameWorkView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

// MARK: - Preferences
struct ViewOffsetKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue = CGFloat.zero
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value += nextValue()
    }
}

//MARK: -全局框架View
struct FrameWorkView: View {
    //MARK: - 常量属性
    static let titleBarHeight:CGFloat = 44                          //common title的高度
    static let bigTitleHeight:CGFloat = 56                          //big title的高度
    static let headerHeight:CGFloat = 120                           //头部高度
    static let textBottomGap:CGFloat = 2.5                          //big title距底部间距
    //MARK: - 变量属性
    @StateObject
    var headerData = PullRecordData.init()
    @State
    private var FadeIn:Bool = false
    @State
    private var offset:CGFloat = 0
    @State
    private var finaloffset:CGFloat = 0
    //MARK: - Body
    var body: some View {
        ZStack(alignment:.top){
            ZStack(alignment:.top){
                GeometryReader { proxy in
                    ScrollView()
                    {
                        VStack(spacing:0){
                            LazyVStack(spacing:0){
                                Color.clear.frame(height: FrameWorkView.bigTitleHeight-FrameWorkView.textBottomGap)                 //高度和big title高度一致，利用onAppear和onDisappear回调实现big和common标题的切换
                                    .onAppear(){
                                        self.FadeIn = false
                                    }
                                    .onDisappear(){
                                        self.FadeIn = true
                                    }
                                Color.clear.frame(height: FrameWorkView.titleBarHeight+Util.sharedInstance.statusBarHeight()-FrameWorkView.bigTitleHeight+FrameWorkView.textBottomGap)
                            }
                            
                            PullView(header: Color.clear.frame(height: FrameWorkView.headerHeight))
                            {
                                VStack(spacing: 0){
                                    HStack(alignment:.bottom,spacing:0){
                                        Text("App").bold().font(.system(size: 36)).frame(alignment:.bottomLeading)
                                        Spacer()
                                    }
                                    .frame(height: FrameWorkView.bigTitleHeight, alignment: .bottomLeading)
                                    .padding(.leading,20)
                                    
                                    ContentView().padding(.top,0)
                                }
                                .background(GeometryReader {
                                    Color.clear.preference(key: ViewOffsetKey.self,
                                                           value: -$0.frame(in: .named("scroll")).origin.y)
                                })
                                .onPreferenceChange(ViewOffsetKey.self) {
                                    offset = $0
                                }
                            }//: PullView
                        }
                    }//: ScrollView
                    .coordinateSpace(name: "scroll")
                    //监听头部下拉
                    .environment(\.headerData, headerData)
                    .backgroundPreferenceValue(HeaderBoundsPreferenceKey.self) { value -> Color in
                        DispatchQueue.main.async {
                            calculate(proxy, value: value)
                        }
                        return Color.clear //返回一个透明背景，无效果，仅用于在视图更新时触发calculate函数
                    }
                }//: GeometryReader
            }//: ZStack
            .background(Color("#f4f4f7".uicolor()))
            .ignoresSafeArea()
            
            LazyVStack(spacing:0)
            {
                Color.clear.frame(height: Util.sharedInstance.statusBarHeight()).padding(0)
                Spacer()
                HStack(spacing:0){
                    Spacer()
                    Text("App")
                        .bold()
                        .font(.system(size: 20))
                        .opacity(FadeIn ? 1.0 : 0.0)
                        .animation(.easeInOut(duration: 0.2))
                    Spacer()
                }
                Spacer()
                Divider()
                    .background(Color("#f5f6f7".uicolor()))
                    .opacity(FadeIn ? 1.0 : 0.0)
                    .animation(.easeInOut(duration: 0.2))
                    .scaleEffect(CGSize(width: 1, height: 2))
                    .padding(0)
            }//:LazyVStack
            .background(Color.clear)
            .blurBackground(style: .systemMaterial)                                                                         //实现背景毛玻璃模糊效果
            .frame(height: Util.sharedInstance.statusBarHeight()+FrameWorkView.titleBarHeight)
            .padding(0)
            .ignoresSafeArea()
            
            HStack{                                                                                                         //隐藏的加载器，显示透明度会随着下拉偏移量变化
                Spacer()
                ProgressView("")
                    .progressViewStyle(.circular)
                    .padding()
                    .opacity(headerData.progress)
                Spacer()
            }
            .background(Color.clear)
            .offset(x: 0, y: FrameWorkView.bigTitleHeight)
        }
        .onAppear(){
            headerData.offset = offset+FrameWorkView.headerHeight                                                           //记录头部偏移
        }
    }
    
    //计算下拉偏移占比量
    private func calculate(_ proxy: GeometryProxy, value: [HeaderBoundsPreferenceKey.Item]) {
        guard let bounds = value.first?.bounds else {
            return
        }
        
        let headerFrame = proxy[bounds]
        let y = headerFrame.minY + headerData.offset
        let threshold = headerFrame.height
        let topDistance: CGFloat = 30.0
        
        if threshold != headerData.thresold {
            headerData.thresold = threshold
        }
        
        var contentOffset = y + threshold
        if contentOffset == 0 {
            headerData.progress = 0.0
        }
        guard contentOffset > topDistance else {
            return
        }
        
        contentOffset -= topDistance
        let progress = Double(contentOffset / threshold)
        headerData.progress = (progress >= 1.0) ? 1.0 : progress
    }
}

//MARK: - Preview
struct FrameWorkView_Previews: PreviewProvider {
    static var previews: some View {
        FrameWorkView()
    }
}
