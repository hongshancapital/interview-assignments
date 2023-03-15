//
//  ListRowView.swift
//  InterviewDemo
//
//  Created by Lays on 2023/3/15.
//

import SwiftUI

struct ListRowView: View {
    @EnvironmentObject var viewModel: ViewMoel
    
    let rowItem: AppMoel
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: rowItem.iconUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(8)
                    .overlay {
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(.secondary, lineWidth: 1)
                            .opacity(0.3)
                    }
            } placeholder: {
                ProgressView()
            }.frame(width: 60, height: 60)
            
            VStack(alignment: .leading) {
                Text(rowItem.trackName).font(.headline).lineLimit(1)
                Text(rowItem.description).font(.subheadline).lineLimit(2).frame(maxWidth: .infinity, alignment: .leading)
            }
            
            Button {
                if rowItem.collected {
                    // cancel collected status with animation will cause crash
                    viewModel.updateCollectedStatus(forItem: rowItem)
                }else {
                    withAnimation {
                        viewModel.updateCollectedStatus(forItem: rowItem)
                    }
                }
            } label: {
                Image(systemName: rowItem.collected ? "heart.fill" : "heart")
                    .symbolRenderingMode(rowItem.collected ? .multicolor : .monochrome)
                    .foregroundColor(.secondary)
                    .scaleEffect(rowItem.collected ? 1.2 : 1, anchor: .center)
            }
            .buttonStyle(.plain)
        }
        .padding(14)
        .background(Color.white)
        .cornerRadius(12)
    }
}
