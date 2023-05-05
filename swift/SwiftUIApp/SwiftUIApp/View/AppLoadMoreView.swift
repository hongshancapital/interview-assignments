//
//  AppLoadMoreView.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/29.
//

import SwiftUI

enum AppLoadMoreState{
	case none
	case loading
	case noMoreData
}

// TODO: -  进一步了解
// 传递进来的state 无法重新渲染？ 还是@State没有传递进来？ to be continued
struct AppLoadMoreView: View {
	@State var state: AppLoadMoreState
    var body: some View {
		HStack{
			Spacer()
			if state != .none{
				if state == .loading{
					ProgressView()
						.padding(.trailing, 5)
				}
				Text(state == .loading ? "Loading..." : "No more data.")
				.foregroundColor(Color(uiColor: .lightGray))
			   Spacer()
			}
		}
    }
}

struct AppLoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
		AppLoadMoreView(state: .none)
    }
}
