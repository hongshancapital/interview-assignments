//
//  FooterView.swift
//  Demo
//
//  Created by 石超 on 2022/6/24.
//

import SwiftUI

enum RefreshState: Int {
    case invalid // 无效
    case stopped // 停止
    case triggered // 触发
    case loading // 加载
}

struct FooterBoundsPreferenceKey: PreferenceKey {
    struct Item {
        let bounds: Anchor<CGRect>
    }
    static var defaultValue: [Item] = []
    
    static func reduce(value: inout [Item], nextValue: () -> [Item]) {
        value.append(contentsOf: nextValue())
    }
}

struct FooterView: View {
    
    @Binding var state: RefreshState
    @Binding var isNoMoreData: Bool
    
    var body: some View {
        
        if state == .stopped {
            HStack(alignment: .bottom) {
                Spacer()
                if !isNoMoreData {
                    ProgressView()
                }
                Text(isNoMoreData ? "No more data." : "Loading...")
                    .offset(CGSize(width: 15, height: 0))
                Spacer()
            }
            .frame(height: 20, alignment: .top)
        } else if state == .triggered {
            HStack(alignment: .bottom) {
                Spacer()
                ProgressView()
                Text("Release loading")
                    .offset(CGSize(width: 15, height: 0))
                Spacer()
            }
            .frame(height: 20, alignment: .top)
        } else if state == .loading {
            HStack(alignment: .bottom) {
                Spacer()
                ProgressView()
                Text("Loading...")
                    .offset(CGSize(width: 15, height: 0))
                Spacer()
            }
            .frame(height: 20, alignment: .top)
        } else if state == .invalid {
            Spacer().frame(height: 20, alignment: .top)
        }
    }
}

struct FooterView_Previews: PreviewProvider {
    static var previews: some View {
        FooterView(state: .constant(.invalid), isNoMoreData: .constant(false))
    }
}
