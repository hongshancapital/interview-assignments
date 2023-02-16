//
//  SALPageLoadingView.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI


struct PageLoadingListView<ViewModel: PageListProtocolViewModel>: View {
    
    @StateObject var viewModel: ViewModel
    
    @ViewBuilder var itemViewAction: (_ index: Int, _ item: ViewModel.T, _ vm: ViewModel) -> AnyView
    
    var body: some View {
        Group{
            if viewModel.loadStatus == .first {
                ProgressView().progressViewStyle(.circular)
            }else if viewModel.loadStatus == .empty || viewModel.loadStatus == .error  {
                if self.viewModel.datas.isEmpty{
                    if viewModel.loadStatus == .empty  {
                        emptyDataView
                    }else{
                        errorView
                    }
                }
            }else{
                listView
            }
        }
        .task {
            await viewModel.refresh()
        }
    }
    
    /// content list view
    private var listView: some View {
        List{
            ForEach (self.viewModel.datas){ item in
                self.itemViewAction(self.viewModel.datas.firstIndex(of: item)!, item, self.viewModel)
                    .foregroundColor(.clear)
            }
            
            footerView
        }.refreshable {
            await self.viewModel.refresh()
        }
        
    }
    
    
    ///no data panel
    private var emptyDataView: some View{
        EmptyDataView(exceptionType: .empty){
            Task(priority: .userInitiated) {
                self.viewModel.updateLoadStatus(status: .first)
                await self.viewModel.refresh()
            }
        }
    }
    
    ///refresh error panel
    private var errorView: some View{
        EmptyDataView(exceptionType: .exception(errMsg:self.viewModel.error.msg())) {
            Task(priority: .userInitiated) {
                self.viewModel.updateLoadStatus(status: .first)
                await self.viewModel.refresh()
            }
        }
    }
    
    //refresh footer
    private var footerView: some View{
        RefreshFooter(isHaveMore: .constant(self.viewModel.isHaveMore)
                      , taskAction: {
            await viewModel.loadMore()
        })
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
    }
    
    
}

#if DEBUG
struct PageLoadingListView_Previews: PreviewProvider {
    static var previews: some View {
        PageLoadingListView<SCDTAppListViewModel> (viewModel:SCDTAppListViewModel() ){ index, item , vm in
            AnyView(AppProductItemRow(model: .constant(item)))
        }
    }
}
#endif
