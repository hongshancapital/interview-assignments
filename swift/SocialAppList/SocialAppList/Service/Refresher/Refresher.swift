//
//  Refresher.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import SwiftUI

extension View {
    func headerRefresh(refreshing: Binding<Bool>, _ refresh: (() -> Void)?) -> some View {
        RefreshHeader(70, refreshing: refreshing, refresh: refresh) { self }
    }
    
    func footerRefresh(hasMore: Binding<Bool>, _ refresh: (() -> Void)?) -> some View {
        RefreshFooter(40, hasMore: hasMore, refresh: refresh) { self }
    }
}

struct RefreshFooter<Content: View>: View {
    private var height: CGFloat = 30
    @Binding var hasMore: Bool
    private var refresh: (() -> Void)?
    private let content: Content
    
    init(_ height: CGFloat = 40,
         hasMore: Binding<Bool>,
         refresh: (() -> Void)?,
         @ViewBuilder content: () -> Content)
    {
        self.height = height
        self._hasMore = hasMore
        self.refresh = refresh
        self.content = content()
    }
    
    var body: some View {
        VStack {
            content
            LazyVStack {
                RefreshFooter(height, hasMore: $hasMore, refresh: refresh)
            }
        }
    }
    
    struct RefreshFooter: View {
        private var height: CGFloat = 30
        @Binding var hasMore: Bool
        private var refresh: (() -> Void)?
        
        init(_ height: CGFloat = 40,
             hasMore: Binding<Bool>,
             refresh: (() -> Void)?)
        {
            self.height = height
            self._hasMore = hasMore
            self.refresh = refresh
        }
        
        var body: some View {
            HStack(alignment: .center, spacing: 5) {
                Spacer()
                if hasMore {
                    ProgressView()
                    Text("Loading...")
                        .foregroundColor(Color("color_footer_text"))
                    
                } else {
                    Text("No more data.")
                        .foregroundColor(Color("color_footer_text"))
                }
                Spacer()
            }
            .frame(height: height)
            .onAppear {
                if hasMore {
                    refresh?()
                }
            }
        }
    }
}

struct RefreshHeader<Content: View>: View {
    @State private var preOffset: CGFloat = 0
    @State private var offset: CGFloat = 0
    @State private var frozen = false
    @State private var progress: CGFloat = 0
    
    private var threshold: CGFloat = 70
    @Binding private var refreshing: Bool
    private var refresh: (() -> Void)?
    private let content: Content
    @State private var hasAnimation: Bool = false
    
    init(_ threshold: CGFloat = 70,
         refreshing: Binding<Bool>,
         refresh: (() -> Void)?,
         @ViewBuilder content: () -> Content)
    {
        self.threshold = threshold
        self._refreshing = refreshing
        self.refresh = refresh
        self.content = content()
    }
    
    var body: some View {
        ScrollView {
            ZStack(alignment: .top) {
                MovingPositionView()

                self.content
                    .alignmentGuide(.top, computeValue: { _ in
                        (self.refreshing && self.frozen) ? -self.threshold : 0
                    })

                RefreshHeader(height: self.threshold,
                              loading: self.refreshing,
                              frozen: self.frozen,
                              progress: self.progress)
            }
            .animation(hasAnimation ? .easeInOut : .none)
        }
        .background(FixedPositionView())
        .onPreferenceChange(RefreshablePreferenceTypes.RefreshablePreferenceKey.self) { values in
            self.calculate(values)
        }
    }
    
    private func calculate(_ values: [RefreshablePreferenceTypes.RefreshablePreferenceData]) {
        DispatchQueue.main.async {
            let movingBounds = values.first(where: { $0.viewType == .movingPositionView })?.bounds ?? .zero
            let fixedBounds = values.first(where: { $0.viewType == .fixedPositionView })?.bounds ?? .zero
            
            self.offset = movingBounds.minY - fixedBounds.minY
            
            self.progress = max(0, min(1, self.offset / self.threshold))

            if !self.refreshing, self.offset > self.threshold, self.preOffset <= self.threshold {
                self.hasAnimation = true
                self.refreshing = true
                self.refresh?()
            }
            
            if self.refreshing {
                if self.preOffset > self.threshold, self.offset <= self.threshold {
                    self.frozen = true
                }
            } else {
                self.frozen = false
            }
            
            self.preOffset = self.offset
        }
    }
    
    private struct FixedPositionView: View {
        var body: some View {
            GeometryReader { proxy in
                Color.clear
                    .preference(key: RefreshablePreferenceTypes.RefreshablePreferenceKey.self,
                                value: [RefreshablePreferenceTypes.RefreshablePreferenceData(viewType: .fixedPositionView, bounds: proxy.frame(in: .global))])
            }
        }
    }
    
    private struct MovingPositionView: View {
        var body: some View {
            GeometryReader { proxy in
                Color.clear
                    .preference(key: RefreshablePreferenceTypes.RefreshablePreferenceKey.self,
                                value: [RefreshablePreferenceTypes.RefreshablePreferenceData(viewType: .movingPositionView, bounds: proxy.frame(in: .global))])
            }
            .frame(height: 0)
        }
    }
    
    private struct RefreshHeader: View {
        var height: CGFloat
        var loading: Bool
        var frozen: Bool
        var progress: CGFloat
        
        var body: some View {
            HStack {
                Spacer()
                
                Group {
                    if loading {
                        VStack {
                            Spacer()
                            ProgressView()
                            Spacer()
                        }
                    } else {
                        Image(systemName: "arrow.down")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .rotationEffect(.degrees(max(0, 180*progress)))
                    }
                }
                .frame(width: height * 0.25, height: height * 0.8)
                .fixedSize()
                .opacity((loading && frozen) ? 1 : progress)
                .offset(y: (loading && frozen) ? 0 : -height)
                
                Spacer()
            }
            .frame(height: height)
        }
    }
}

private struct RefreshablePreferenceTypes {
    enum ViewType: Int {
        case fixedPositionView
        case movingPositionView
    }
    
    struct RefreshablePreferenceData: Equatable {
        let viewType: ViewType
        let bounds: CGRect
    }
    
    struct RefreshablePreferenceKey: PreferenceKey {
        static var defaultValue: [RefreshablePreferenceData] = []
        
        static func reduce(value: inout [RefreshablePreferenceData],
                           nextValue: () -> [RefreshablePreferenceData]) {
            value.append(contentsOf: nextValue())
        }
    }
}
