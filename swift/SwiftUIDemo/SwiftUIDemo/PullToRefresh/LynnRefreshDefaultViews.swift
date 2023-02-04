//
//  LynnRefreshDefaultViews.swift
//  SwiftUIPullToRefresh
//
//  Created by hbc on 2022/2/19.
//

import SwiftUI

class ListState: ObservableObject {
    @Published private(set) var noMore: Bool
    
    init() {
        noMore = false
    }
    
    func setNoMore(_ newNoMore: Bool) {
        noMore = newNoMore
    }
    
    func getnoMore() -> Bool {
        return noMore
    }
}

struct RefreshDefaultHeader: View {
    
    @Environment(\.headerRefreshData) private var headerRefreshData
    
    var body: some View {
        let state = headerRefreshData.refreshState
//        let progress = headerRefreshData.progress
        if state == .stopped {
            VStack(spacing: 0){
                Spacer()
            }
        }
        if state == .triggered {
            VStack(spacing: 0){
                Spacer()
            }
        }
        if state == .loading {
            ProgressView()
                .padding(.top, ScreenSafeHead()-20)
                .frame(height: 60)
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
    @EnvironmentObject private var listState: ListState
    
    var body: some View {
        let state = footerRefreshData.refreshState
        
        if state == .stopped {
            HStack(spacing: 0){
                Spacer().frame(height: 40)
            }
        }
        if state == .triggered {
            Spacer()
                .frame( height: 60)
        }
        if state == .loading {
            HStack {
                ProgressView()
                    .padding(.trailing,10)
                    .frame(height: 50)
                Text("Loading...")
                    .foregroundColor(Color.gray)
            }.padding(.top,-20)
            .padding(.bottom,20)
            
        }
        if state == .invalid {
            Spacer()
                .padding()
                .frame(height: 60)
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

