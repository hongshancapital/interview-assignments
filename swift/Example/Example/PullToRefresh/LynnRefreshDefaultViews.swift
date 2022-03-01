//
//  LynnRefreshDefaultViews.swift
//  SwiftUIPullToRefresh
//
//  Created by apple on 2021/7/14.
//

import SwiftUI

class ReloadContexts: ObservableObject {
    @Published private(set) var noMore: Bool
    
    init() {
        noMore = false
    }
    
    func setNoMore(_ newNoMore: Bool) {
        noMore = newNoMore
    }
}

struct RefreshDefaultHeader: View {
    
    @Environment(\.headerRefreshData) private var headerRefreshData
    @EnvironmentObject private var listState: ReloadContexts

    var body: some View {
        let state = headerRefreshData.refreshState
        //let progress = headerRefreshData.progress
        if state == .stopped {
            Text("下拉刷新").frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
        }
        if state == .triggered {
            Text("松手加载").frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
        }
        if state == .loading {
            ProgressView("加载中...")
                .padding()
                .frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
        }
        if state == .invalid {
            Spacer()
                .padding()
                .frame(height: 60)
        }
    }
}

struct RefreshDefaultFooter: View {
    
    @Environment(\.footerRefreshData) private var footerRefreshData
    @EnvironmentObject private var listState: ReloadContexts
    
    var body: some View {
        let state = footerRefreshData.refreshState
        //let progress = footerRefreshData.progress
        if state == .stopped {
            Text(listState.noMore ? "没有更多了" : "上拉加载更多").frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
            
        }
        if state == .triggered {
            Text("松手加载").frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
        }
        if state == .loading {
            ProgressView("加载中...")
                .padding()
                .frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
        }
        if state == .invalid {
            Spacer().padding().frame(height: 60)
        }
    }
    
    private func printLog(_ state: RefreshState) -> some View {
        print("\(state)")
        return EmptyView()
    }
}


struct LynnProgressViewStyle: ProgressViewStyle {
    var strokeColor = Color.pink
    var strokeWidth = 10.0
    
    func makeBody(configuration: Configuration) -> some View {
        let fractionCompleted = configuration.fractionCompleted ?? 0
        
        return ZStack {
            Circle()
                .trim(from: 0, to: CGFloat(fractionCompleted))
                .stroke(strokeColor, style: StrokeStyle(lineWidth: CGFloat(strokeWidth), lineCap: .round))
                .rotationEffect(.degrees(-90))
        }
    }
}

struct CustomizeProgressViewStyle: ProgressViewStyle {

    func makeBody(configuration: Configuration) -> some View {
        
        let degrees = configuration.fractionCompleted ?? 0
        let percent = Int(configuration.fractionCompleted ?? 100)
        
        return VStack {
            
            MyCircle(startAngle: .degrees(1), endAngle: .degrees(degrees))
                .frame(width: 200, height: 200)
                .padding(50)
            Text("Task \(percent)% Complete")
        }
    }
}
 
struct MyCircle: Shape {
    var startAngle: Angle
    var endAngle: Angle
 
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.addArc(center: CGPoint(x: rect.midX, y: rect.midY),
                 radius: rect.width / 2, startAngle: startAngle,
                              endAngle: endAngle, clockwise: true)
 
        return path.strokedPath(.init(lineWidth: 100, dash: [5, 3],
                 dashPhase: 10))
    }
}
