//
//  RefreshFooter.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/16.
//

import SwiftUI

struct RefreshFooter: View {
    @Binding var isHaveMore : Bool
    var taskAction:  () async -> Void
    
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            Spacer()
            // Refresh success, should check if show load more
            if isHaveMore {
                ProgressView()
                    .progressViewStyle(.circular)

                Text("Loading...")
            }else{
                Text("No more data.")
            }
            Spacer()
        }
        .frame(height: 30)
        .task {
            if isHaveMore {
                await self.taskAction()
            }
        }
        .foregroundColor(.secondary)
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
    }
}

#if DEBUG
struct RefreshFooter_Previews: PreviewProvider {
    static var previews: some View {
        RefreshFooter(isHaveMore: .constant(true), taskAction: {
            
        })
    }
}
#endif
