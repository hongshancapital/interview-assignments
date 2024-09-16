//
//  LoadingView.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import SwiftUI

struct LoadingView: View {
    @EnvironmentObject var model: DemoViewModel
    var pro = ProgressView()
    var body: some View {
        HStack {
            if model.isLoadingMore {
                ProgressView()
                Text("Loading...")
                    .foregroundColor(Color.gray)
                    .padding(.leading, 5)
            } else {
                Text("No more data.")
                    .foregroundColor(Color.gray)
                    .padding(.leading, 5)
            }
        }
        .onAppear(perform: {
            Task {
                await model.loadingMore()
            }
        })
        .listRowBackground(Color(.displayP3, red: 242 / 255, green: 242 / 255, blue: 247 / 255, opacity: 1))
        .frame(maxWidth: .infinity, alignment: .center)
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}

