//
//  AppInfoView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import SwiftUI

struct AppIntroView: View {
    var title: String
    var detail: String
    var body: some View {
        VStack(alignment: .leading) {
            Text(verbatim: title)
                .bold()
                .lineLimit(1)
                .font(.headline)
                .padding(.bottom, 1)
            Text(verbatim: detail)
                .lineLimit(2)
                .font(.subheadline)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct AppInfoView: View {
    
    @ObservedObject var viewModel: AppInfoViewModel
    
    var body: some View {
        HStack(spacing: 6) {
            RoundedCornerImage(
                imageUrl: viewModel.app.artworkUrl100
            )
            .frame(width: 67, height: 67, alignment: .center)
                        
            AppIntroView(
                title: viewModel.app.trackName,
                detail: viewModel.app.description.replacingOccurrences(of: "\n", with: "")
            )
            
            CollectButton(isCollected: $viewModel.isCollected) {
                viewModel.isCollected.toggle()
            }
        }
        .padding(14)
        .background(Color(UIColor.white))
        .cornerRadius(10)
        .frame(maxHeight: .infinity)
    }
}


struct AppInfoView_Previews: PreviewProvider {
    static var previews: some View {
        AppInfoView(viewModel: AppInfoViewModel(app: AppInfo.mockedData[3]))
    }
}
