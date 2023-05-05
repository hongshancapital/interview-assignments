//
//  ListEntryView.swift
//  InterviewDemo
//
//  Created by Chenjun Ren on 2022/4/7.
//

import SwiftUI

struct ListEntry: View {
    @EnvironmentObject var viewModel: ContentViewModel
    
    let entryItem: AppInfo
    
    var body: some View {
        HStack {
            // AppIcon image
            AsyncImage(url: URL(string: entryItem.iconUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.secondary, lineWidth: 1)
                            .opacity(0.3)
                    }
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)
            
            // description
            VStack(alignment: .leading, spacing: 6) {
                Text(entryItem.name)
                    .font(.callout.bold())
                    .lineLimit(1)
                    .minimumScaleFactor(0.5)
                
                Text(entryItem.description)
                    .font(.caption.weight(.medium).leading(.tight))
                    .lineLimit(2)
            }
            
            Spacer(minLength: 15)
            
            // like button
            Button {
                withAnimation {
                    viewModel.updateLikeStatus(for: entryItem)
                }
            } label: {
                Image(systemName: entryItem.isLiked ? "heart.fill" : "heart")
                    .symbolRenderingMode(entryItem.isLiked ? .multicolor : .monochrome)
                    .foregroundColor(.secondary)
                    .scaleEffect(entryItem.isLiked ? 1.15 : 1, anchor: .center)
            }
            .buttonStyle(.plain)
        }
        .padding()
        .background(Color(uiColor: .systemBackground))
        .cornerRadius(10)
    }
}
